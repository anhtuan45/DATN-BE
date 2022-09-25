package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.mail.ResetPasswordMailDto;
import com.ws.masterserver.dto.customer.reset_password.ResetPasswordDto;
import com.ws.masterserver.dto.customer.user.register.RegisterDto;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.CustomerDetailService;
import com.ws.masterserver.service.MailService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.customer.reset_password.ResetPasswordValidator;
import com.ws.masterserver.utils.validator.customer.user.RegisterValidator;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerDetailServiceImpl implements CustomerDetailService {
    private final WsRepository repository;
    private final MailService mailService;
    private final Configuration configuration;

    @Value("${spring.mail.username}")
    private String email;

    private static final String CONTENT = "Cảm ơn bạn đã đăng ký tài khoản tại Woman-Shirt. Chúng tôi xin gửi tặng bạn Mã khuyến mãi: %s(%s)";
    private static final String TEMPLATE = "Cảm ơn bạn đã đăng ký tài khoản tại <b>Woman-Shirt</b>. Chúng tôi xin gửi tặng bạn Mã khuyến mãi: <b>%s</b> (%s)";
    private static final String DOMAIN_CLIENT = "http://localhost:4200/reset-password/%s";
    private static final long MINUS_TO_EXPIRED = 10;
    private static final String RESET_PASSWORD_TEMPLATE_NAME = "reset_password.ftl";
    private static final String FROM = "WOMAN SHIRT<%s>";
    private static final String SUBJECT = "QUÊN MẬT KHẨU";


    /**
     * KH đăng ký tài khoản
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public Object register(RegisterDto dto) {
        RegisterValidator.validDtoData(dto);
        RegisterValidator.validDtoConstrains(dto, repository);
        PasswordEncoder passwordEncoder = BeanUtils.getBean(BCryptPasswordEncoder.class);
        UserEntity user = UserEntity.builder()
                .id(UidUtils.generateUid())
                .role(RoleEnum.ROLE_CUSTOMER)
                .active(true)
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
//                .gender(Boolean.getBoolean(dto.getGender()))
                .dob(DateUtils.toDate(dto.getDob(), DateUtils.F_DDMMYYYY))
                .password(passwordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone().trim())
                .email(dto.getEmail().trim())
                .build();
        log.info("register() user before save: {}", JsonUtils.toJson(user));
        repository.userRepository.save(user);
        log.info("register() user after save: {}", JsonUtils.toJson(user));

        // TODO: 24/07/2022: Sau khi KH đăng kí tài khoản thành công, hệ thống sẽ tự động gửi notification + email với
        //  nội dụng tặng mã Khuyến mãi cho KH mới. Sử dụng đa luồng và chỉ ghi lại log(không throw exception)
        /**
         * get mã KM giành cho những KH mới
         * */
//        var discount4NewCustomer = repository.discountRepository.findByDiscountTypeName4NewCustomer(DiscountSuperTypeEnums.NEW_CUSTOMER.name());
//        CompletableFuture sendMailCF = CompletableFuture.runAsync(() -> {
//        });
//        CompletableFuture saveNotificationCF = CompletableFuture.runAsync(() -> {
//
//        });
//        try {
//            CompletableFuture.allOf(sendMailCF, saveNotificationCF).get();
//        } catch (Exception e) {
//            log.error("register() error: {}", e.getMessage());
//        }

        String customerId = user.getId();
        NotificationEntity notification = NotificationUtils.buildNotification(
                String.format(NotificationUtils.REGISTER_CUSTOMER_TEMP, user.getFirstName().concat(" ").concat(user.getLastName())),
                ObjectTypeEnum.USER.name(),
                customerId,
                NotificationTypeEnum.NORMAL.name());
        repository.notificationRepository.save(notification);

        /**
         * add các mã KM cps customerType ='+ 'ALL'
         */
        List<DiscountEntity> discountEntities= repository.discountRepository
                .findByCustomerTypeAll(DiscountCustomerTypeEnums.ALL.name(), DiscountStatusEnums.ACTIVE.name());
        if (!discountEntities.isEmpty()){
            for (DiscountEntity discount : discountEntities) {
                Long usageLimit = discount.getUsageLimit();
                if (usageLimit != null) {
                    String discountId = discount.getId();
                    Long discountUsageNow = repository.discountRepository.getUsageNumberNow(discountId);
                    if (discountUsageNow < usageLimit) {
                        repository.discountCustomerRepository.save(
                                DiscountCustomerEntity.builder()
                                        .id(UidUtils.generateUid())
                                        .discountId(discountId)
                                        .userId(customerId)
                                .build());
                    }
                }
            }
        }
        return ResData.ok(customerId);
    }

    @Override
    @Transactional
    public Object sendMail4ForgotPass(String email) {
        log.info("sendMail4ForgotPass() start with payload: {}", email);
        if (StringUtils.isNullOrEmpty(email)) {
            throw new WsException(WsCode.EMAIL_NOT_BLANK);
        }
        UserEntity customer = repository.userRepository.findByEmailIgnoreCaseAndActiveAndRole(email.trim(), Boolean.TRUE, RoleEnum.ROLE_CUSTOMER);
        log.info("sendMail4ForgotPass() find customer: {}", JsonUtils.toJson(customer));
        if (null == customer) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        if (repository.resetTokenRepository.check5TimesInDay(customer.getId())) {
            throw new WsException(WsCode.MAX_SEND_OTP);
        }
        String token = UidUtils.generateToken(6);
        while (repository.resetTokenRepository.existsByTokenAndActive(token, true)) {
            token = UidUtils.generateToken(6);
        }
        ResetTokenEntity resetToken = ResetTokenEntity.builder()
                .id(UidUtils.generateUid())
                .token(token)
                .userId(customer.getId())
                .createdDate(new Date())
                .active(true)
                .build();
        log.info("sendMail4ForgotPass() resetToken before save: {}", JsonUtils.toJson(resetToken));
        repository.resetTokenRepository.save(resetToken);
        log.info("sendMail4ForgotPass() resetToken after save: {}", JsonUtils.toJson(resetToken));

        Map model = buildModel(customer, resetToken);

        try {
            freemarker.template.Template template = configuration.getTemplate(RESET_PASSWORD_TEMPLATE_NAME);
            ResetPasswordMailDto rsmd = ResetPasswordMailDto.builder()
                    .from(String.format(FROM, email))
                    .to(customer.getEmail())
                    .text(FreeMarkerTemplateUtils.processTemplateIntoString(template, model))
                    .subject(SUBJECT)
                    .build();
            log.info("sendMail4ForgotPass() build rsmd: {}", JsonUtils.toJson(rsmd));
            mailService.sendWForgotPassword(rsmd).get();
            return ResData.ok(resetToken.getId());
        } catch (Exception e) {
            log.error("sendMail4ForgotPass() error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    @Override
    public Object resetPassword(ResetPasswordDto payload) {
        log.info("resetPassword() payload: {}", JsonUtils.toJson(payload));
        ResetPasswordValidator.validDto(payload);
        ResetPasswordValidator.validConstraint(repository, payload);
        ResetTokenEntity resetToken = repository.resetTokenRepository.findByTokenActive(payload.getToken(), true);
        log.info("resetPassword() resetToken: {}", JsonUtils.toJson(resetToken));
        UserEntity user = repository.userRepository.findByIdAndActive(resetToken.getUserId(), true);
        BCryptPasswordEncoder passwordEncoder = BeanUtils.getBean(BCryptPasswordEncoder.class);
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        log.info("resetPassword() user before save: {}", JsonUtils.toJson(user));
        repository.userRepository.save(user);
        log.info("resetPassword() user after save: {}", JsonUtils.toJson(user));
        resetToken.setActive(false);
        log.info("resetPassword() resetToken before save: {}", JsonUtils.toJson(resetToken));
        repository.resetTokenRepository.save(resetToken);
        log.info("resetPassword() resetToken after save: {}", JsonUtils.toJson(resetToken));
        return user.getId();
    }

    private static Map buildModel(UserEntity customer, ResetTokenEntity resetToken) {
        Map<String, Object> model = new HashMap<>();
        model.put("time", DateUtils.toStr(new Date(), DateUtils.F_DDMMYYYYHHMM));
        model.put("name", customer.getFirstName() + " " + customer.getLastName());
        model.put("email", customer.getEmail());
        model.put("link", String.format(DOMAIN_CLIENT, resetToken.getToken()));
        model.put("minusToExpired", MINUS_TO_EXPIRED);
        log.info("sendMail4ForgotPass() build model: {}", JsonUtils.toJson(model));
        return model;
    }
}
