package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountCustomerRepository extends JpaRepository<DiscountCustomerEntity, String> {
    @Query("select count(d) from DiscountCustomerEntity d where d.discountId = ?1")
    Long countByDiscountId(String id);

    @Query("select d from DiscountCustomerEntity d where d.discountId = ?1")
    List<DiscountCustomerEntity> findByDiscountId(String id);

    @Query("select count(dc.id) > 0\n" +
            "from DiscountCustomerEntity dc\n" +
            "where dc.discountId = ?1 and dc.userId = ?2")
    boolean checkCustomerAvailableCaseCustomer(String discountId, String userId);

    @Query("select count(cg.id) > 0\n" +
            "from CustomerGroupEntity cg\n" +
            "left join CustomerTypeEntity ct on ct.id = cg.customerTypeId\n" +
            "left join DiscountCustomerTypeEntity dct on dct.customerTypeId = ct.id\n" +
            "where dct.discountId = ?1 and cg.userId = ?2")
    boolean checkCustomerAvailableCaseGroup(String discountId, String userId);
}
