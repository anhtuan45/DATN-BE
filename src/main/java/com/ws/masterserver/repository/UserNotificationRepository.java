package com.ws.masterserver.repository;

import com.ws.masterserver.entity.NotificationEntity;
import com.ws.masterserver.entity.UserNotificationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, String> {

    Boolean existsByUserIdAndNotificationId(String userId, String notificationId);

    @Query("select count(n.id)\n" +
            "from NotificationEntity n\n" +
            "left join UserNotificationEntity un on un.notificationId = n.id\n" +
            "where n.userId is null\n" +
            "and un.userId <> :userId or un.userId is null")
    Long countUnreadNumber(@Param("userId") String userId);

    @Query("select no\n" +
            "from NotificationEntity no\n" +
            "where no.userId = :userId")
    List<NotificationEntity> findNotificationByUserId(@Param("userId") String userId, Pageable pageable);
}
