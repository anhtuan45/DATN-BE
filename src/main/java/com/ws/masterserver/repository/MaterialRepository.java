package com.ws.masterserver.repository;

import com.ws.masterserver.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author myname
 */
@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, String> {
    MaterialEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);

    @Query("select m from MaterialEntity m where m.active = ?1")
    List<MaterialEntity> findByActive(boolean b);
}
