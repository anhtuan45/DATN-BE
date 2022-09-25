package com.ws.masterserver.repository;

import com.ws.masterserver.entity.NotificationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {

    boolean existsById(String id);

    @Query("select no\n" +
            "from NotificationEntity no\n" +
            "where no.userId is null")
    List<NotificationEntity> find4Elements4Admin(Pageable pageable);

    @Query("select n.id from NotificationEntity n where n.userId is null")
    List<String> find4Admin();

    @Query("select count(n.id)\n" +
            "from NotificationEntity n\n" +
            "where n.userId = :userId and n.isRead = false")
    Long countUnreadNumberNoti4Customer(@Param("userId") String userId);

    @Query("select n.id from NotificationEntity n where n.userId = :userId")
    List<String> findListNotification4Customer(@Param("userId") String userId);


}
