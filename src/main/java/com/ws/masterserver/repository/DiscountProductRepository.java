package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProductEntity, String> {
    @Query("select count(d) from DiscountProductEntity d where d.discountId = ?1")
    Long countByDiscountId(String discountId);

    @Query("select d from DiscountProductEntity d where d.discountId = ?1")
    List<DiscountProductEntity> findByDiscountId(String id);

    @Query("select d from DiscountProductEntity d\n" +
            "left join ProductEntity p on d.productId = p.id\n" +
            "where d.discountId = ?1 and p.active = true")
    List<DiscountProductEntity> findByDiscountIdAndProductActive(String id);
}
