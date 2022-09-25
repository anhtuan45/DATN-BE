package com.ws.masterserver.dto.admin.notification;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationRes {
    /**
     * danh sach thong bao
     */
    private List<NotificationDto> notifications;

    /**
     * so thong bao chua doc
     */
    private Long unreadNumber;
}
