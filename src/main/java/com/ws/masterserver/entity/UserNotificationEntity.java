package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Table(name = "user_notification")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNotificationEntity {

    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "notification_id")
    private String notificationId;
}
