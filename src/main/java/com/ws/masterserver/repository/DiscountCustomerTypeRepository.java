package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountCustomerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountCustomerTypeRepository extends JpaRepository<DiscountCustomerTypeEntity, String> {
    @Query("select count(d) from DiscountCustomerTypeEntity d where d.discountId = ?1")
    Long countByDiscountId(String id);

    @Query("select d from DiscountCustomerTypeEntity d where d.discountId = ?1")
    List<DiscountCustomerTypeEntity> findByDiscountId(String id);

//    @Query("select count() > 0\n" +
//            "from UserEntity u")
//    boolean checkCustomerAvailable(String discountId, String userId);

}
