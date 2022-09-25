package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.notification.NotificationDto;
import com.ws.masterserver.dto.admin.notification.NotificationRes;
import com.ws.masterserver.entity.NotificationEntity;
import com.ws.masterserver.entity.UserNotificationEntity;
import com.ws.masterserver.service.NotificationService;
import com.ws.masterserver.service.WebSocketService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;
import com.ws.masterserver.utils.constants.enums.ObjectTypeEnum;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final WsRepository repository;
    private final WebSocketService wsService;

    /**
     * @param currentUser
     * @return
     */
    @Override
    public Object get4Admin(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        @NotNull List<NotificationDto> notifications = getNotificationDtos(currentUser, 3);
        return ResData.ok(NotificationRes.builder()
                .notifications(notifications)
                .unreadNumber(repository.userNotificationRepository.countUnreadNumber(currentUser.getId()))
                .build());
    }

    @NotNull
    private List<NotificationDto> getNotificationDtos(CurrentUser currentUser, Integer pageSize) {
        List<NotificationDto> notifications = new ArrayList<>();
        List<com.ws.masterserver.entity.NotificationEntity> notificationEntities = repository.notificationRepository.find4Elements4Admin(PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "createdDate")));
        if (!notificationEntities.isEmpty()) {
            notifications = notificationEntities.stream().map(obj -> {
                NotificationTypeEnum type = NotificationTypeEnum.from(obj.getType());
                return NotificationDto.builder()
                        .id(obj.getId())
                        .content(obj.getContent())
                        .createdDate(DateUtils.toStr(obj.getCreatedDate(), DateUtils.DATE_TIME_FORMAT_VI))
                        .div(type == null ? null : type.getDiv())
                        .icon(type == null ? null : type.getIcon())
                        .isRead(repository.userNotificationRepository.existsByUserIdAndNotificationId(currentUser.getId(), obj.getId()))
                        .objectType(obj.getObjectType())
                        .objectTypeId(obj.getObjectTypeId())
                        .build();
            }).collect(Collectors.toList());
        }
        return notifications;
    }

    @NotNull
    private List<NotificationDto> getNotification4Customer(CurrentUser currentUser, Integer pageSize) {
        List<NotificationDto> notifications = new ArrayList<>();
        List<com.ws.masterserver.entity.NotificationEntity> notificationEntities = repository.userNotificationRepository.findNotificationByUserId(currentUser.getId(),PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "createdDate")));
        if (!notificationEntities.isEmpty()) {
            notifications = notificationEntities.stream().map(obj -> NotificationDto.builder()
                    .id(obj.getId())
                    .content(obj.getContent())
                    .createdDate(DateUtils.toStr(obj.getCreatedDate(), DateUtils.DATE_TIME_FORMAT_VI))
//                    .div(obj.getType().getDiv())
//                    .icon(obj.getType().getIcon())
                    .div(NotificationTypeEnum.NORMAL.getDiv())
                    .icon(ObjectTypeEnum.ORDER.getValue())
                    .isRead(obj.getIsRead())
                    .objectType(obj.getObjectType())
                    .objectTypeId(obj.getObjectTypeId())
                    .build()).collect(Collectors.toList());
        }
        return notifications;
    }

    @Override
    @Transactional
    public Long readTop3Notification4Admin(CurrentUser currentUser, List<String> dto) {
        log.info("NotificationServiceImpl read4admin start");
        if (!dto.isEmpty()) {
            saveUserNotifications(currentUser, dto);
        }
        log.info("NotificationServiceImpl read4admin finish");
        return repository.userNotificationRepository.countUnreadNumber(currentUser.getId());
    }

    private void saveUserNotifications(CurrentUser currentUser, List<String> dto) {
        dto.forEach(obj -> {
            if (!repository.userNotificationRepository.existsByUserIdAndNotificationId(currentUser.getId(), obj)) {
                repository.userNotificationRepository.save(UserNotificationEntity.builder()
                        .id(UidUtils.generateUid())
                        .userId(currentUser.getId())
                        .notificationId(obj)
                        .build());
            }
        });
    }

    @Override
    public Object search4Admin(CurrentUser currentUser, Integer pageSize) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_ADMIN);
        return getNotificationDtos(currentUser, pageSize);
    }

    @Override
    @Transactional
    public Long readAll4Admin(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_ADMIN);
        List<String> notification4Admin = repository.notificationRepository.find4Admin();
        if (!notification4Admin.isEmpty()) {
            saveUserNotifications(currentUser, notification4Admin);
        }
        return -1L;
    }

    @Override
    @Transactional
    public void readById4Admin(CurrentUser currentUser, String id) {
        log.info("NotificationServiceImpl readById4Admin start with payload: {}", id);
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        if (!repository.notificationRepository.existsById(id)) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        if (!repository.userNotificationRepository.existsByUserIdAndNotificationId(currentUser.getId(), id)) {
            UserNotificationEntity userNotification = UserNotificationEntity.builder()
                    .id(UidUtils.generateUid())
                    .userId(currentUser.getId())
                    .notificationId(id)
                    .build();
            repository.userNotificationRepository.save(userNotification);
            log.info("NotificationServiceImpl readById4Admin save userNotificationEntity: {}", JsonUtils.toJson(userNotification));

            wsService.changeUnreadNumberNotification4Admin();
        }
    }

    @Override
    @Transactional
    public Object get4Customer(CurrentUser currentUser, Integer pageSize) {
        AuthValidator.checkCustomerAndStaff(currentUser);
        return getNotification4Customer(currentUser,pageSize);
    }

    @Override
    @Transactional
    public Object getTop3Noti4Customer(CurrentUser currentUser) {
        AuthValidator.checkCustomerAndStaff(currentUser);
        @NotNull List<NotificationDto> notifications = getNotification4Customer(currentUser, 3);
        return ResData.ok(NotificationRes.builder()
                .notifications(notifications)
                .unreadNumber(repository.notificationRepository.countUnreadNumberNoti4Customer(currentUser.getId()))
                .build());
    }

    @Override
    public void readNotification4Customer(CurrentUser currentUser, String id) {
        log.info("NotificationServiceImpl readNotification4Customer start with payload: {}", id);
        AuthValidator.checkCustomerAndStaff(currentUser);
        if (!repository.notificationRepository.existsById(id)) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }

        NotificationEntity notification = repository.notificationRepository.findById(id)
                .orElseThrow(() -> new WsException(WsCode.NOTIFICATION_NOT_FOUND));

        notification.setIsRead(Boolean.TRUE);

        repository.notificationRepository.save(notification);
        log.info("NotificationServiceImpl readNotification4Customer save userNotificationEntity: {}", JsonUtils.toJson(notification));
    }

    @Override
    public Long readAll4Customer(CurrentUser currentUser) {
        AuthValidator.checkCustomerAndStaff(currentUser);
        List<String> notification4Customer = repository.notificationRepository.findListNotification4Customer(currentUser.getId());

        notification4Customer.forEach(obj -> {
            if (repository.notificationRepository.existsById(obj)) {

                NotificationEntity notification = repository.notificationRepository.findById(obj)
                        .orElseThrow(() -> new WsException(WsCode.NOTIFICATION_NOT_FOUND));

                notification.setIsRead(Boolean.TRUE);
                repository.notificationRepository.save(notification);
            }
        });

        return -1L;
    }
}
