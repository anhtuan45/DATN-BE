package com.ws.masterserver.service.impl;

import com.ws.masterserver.entity.NotificationEntity;
import com.ws.masterserver.service.WebSocketService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;
import com.ws.masterserver.utils.constants.enums.ObjectTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate ws;

    private final WsRepository repository;

    @Override
    public void changeUnreadNumberNotification4Admin() {
        log.info("WebSocketServiceImpl changeUnreadNumberNotification4Admin start...");
        this.send("/topic/admin/notification", true);
        log.info("WebSocketServiceImpl changeUnreadNumberNotification4Admin done...");
    }

    @Override
    @Transactional
    public void testNotification4Admin(NotificationTypeEnum type, String content, ObjectTypeEnum objectType, String objectTypeId) {
        repository.notificationRepository.save(NotificationEntity.builder()
                        .id(UidUtils.generateUid())
                        .content(StringUtils.isNullOrEmpty(content) ? "Notification Test " + new Date() : content)
                        .createdDate(new Date())
                        .type(type.name())
                        .objectType(objectType.name())
                        .objectTypeId(objectTypeId)
                .build());
        this.changeUnreadNumberNotification4Admin();
    }

    @Override
    public void testDashboard4Admin(int type) {
        switch (type) {
            case 1:
                this.createdPendingOrder();
                break;
            case 2:
                break;
            default:
                break;
        }
        ws.convertAndSend("/admin/dashboard", true);
    }

    @Override
    public void testV2(String message) {
        ws.convertAndSend("/topic/messages", message);
    }

    private void createdPendingOrder() {

    }

    private void send(String des, Object payload) {
        log.info("WebSocketServiceImpl send start with des: {} and payload {}", des, JsonUtils.toJson(payload));
        try {
            ws.convertAndSend(des, payload);
        } catch (Exception e) {
            log.error("WebSocketServiceImpl send error: {}", e.getMessage());
        }
        log.info("WebSocketServiceImpl send done...");
    }
}
