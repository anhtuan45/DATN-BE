package com.ws.masterserver.repository;

import com.ws.masterserver.entity.BodyWeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyWeightRepository extends JpaRepository<BodyWeightEntity, String> {

    @Query("select w from BodyWeightEntity w where w.minWeight <= ?1 and w.maxWeight >= ?1")
    BodyWeightEntity findByWeight(Long valueOf);
}
