package com.ws.masterserver.repository;

import com.ws.masterserver.entity.CustomerGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerGroupRepository extends JpaRepository<CustomerGroupEntity, String> {
    @Query("select count(c) from CustomerGroupEntity c where c.customerTypeId = ?1")
    Long countByCustomerTypeId(String id);

    @Query("select c from CustomerGroupEntity c where c.customerTypeId = ?1")
    List<CustomerGroupEntity> findByCustomerTypeId(String id);

    @Modifying
    @Query("delete from CustomerGroupEntity c\n" +
            "where c.userId = ?1")
    void deleteByUserId(String userId);
}
