package com.ws.masterserver.repository;

import com.ws.masterserver.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author myname
 */
public interface TypeRepository extends JpaRepository<TypeEntity, String> {
    boolean existsByIdAndActive(String typeId, boolean b);
}
