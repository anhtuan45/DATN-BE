package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetTokenEntity, String> {

    @Query("select count(r.id) >= 5\n" +
            "from ResetTokenEntity r\n" +
            "where r.userId = :userId and cast(r.createdDate as date) = current_date")
    boolean check5TimesInDay(@Param("userId") String userId);

    @Query("select r from ResetTokenEntity r where r.token = ?1 and r.active = ?2")
    ResetTokenEntity findByTokenActive(String token, Boolean active);

    @Query("select (count(r) > 0) from ResetTokenEntity r where r.token = ?1 and r.active = ?2")
    boolean existsByTokenAndActive(String token, boolean b);
}
