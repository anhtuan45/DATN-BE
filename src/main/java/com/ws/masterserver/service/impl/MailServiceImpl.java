package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.mail.OrderInfoMail;
import com.ws.masterserver.dto.customer.mail.ResetPasswordMailDto;
import com.ws.masterserver.dto.customer.user.CustomerResponse;
import com.ws.masterserver.dto.dob.DiscountNotificationDto;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.service.MailService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    public static final String DISCOUNT_EMAIL_SUBJECT = "KHUYẾN MÃI";
    private String from = "hungnnit98@gmail.com";
    private static final String SEND_DISCOUNT_EMAIL = "Hệ thống gửi tặng bạn mã KM: %s";
    private final JavaMailSender sender;
    private final WsRepository repository;

    @Override
    @Transactional
    public CompletableFuture<Void> sendWForgotPassword(ResetPasswordMailDto payload) throws MessagingException {

        MimeMessage mm = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mm, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                helper.setFrom(payload.getFrom());
                helper.setTo(payload.getTo());
                helper.setText(payload.getText(), true);
                helper.setSubject(payload.getSubject());
                sender.send(mm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return future;
    }

    @Override
    public CompletableFuture<Void> send4OrderInfo(OrderInfoMail orderInfo) throws MessagingException {
        MimeMessage mm = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mm, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                helper.setFrom(orderInfo.getFrom());
                helper.setTo(orderInfo.getTo());
                helper.setText(orderInfo.getText(), true);
                helper.setSubject(orderInfo.getSubject());
                sender.send(mm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return future;
    }

    @Override
    public void sendDiscountNotification(DiscountNotificationDto dto) {
        log.info("sendDiscountNotification() dto: {}", JsonUtils.toJson(dto));
        DiscountEntity discount = repository.discountRepository.findByIdAndDeleted(dto.getDiscountId(), false);
        UserEntity customer = repository.userRepository.findCustomerByEmail(dto.getEmail());
        if (discount == null || customer == null || StringUtils.isNullOrEmpty(customer.getEmail())) {
            return;
        }
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom(from);
                simpleMailMessage.setTo(customer.getEmail());
                simpleMailMessage.setText(String.format(SEND_DISCOUNT_EMAIL, discount.getCode()));
                simpleMailMessage.setSubject(DISCOUNT_EMAIL_SUBJECT);
                sender.send(simpleMailMessage);
                log.info("sendDiscountNotification() done!");

            });
            future.get();
        } catch (Exception e) {
            log.info("sendDiscountNotification() e: ", e.getMessage());
        }
    }
}
