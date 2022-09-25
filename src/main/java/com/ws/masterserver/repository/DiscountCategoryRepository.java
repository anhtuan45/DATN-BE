package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DiscountCategoryRepository extends JpaRepository<DiscountCategoryEntity, String> {
    @Query("select count(d) from DiscountCategoryEntity d where d.discountId = ?1")
    Long countByDiscountId(String discountId);

    @Query("select d from DiscountCategoryEntity d where d.discountId = ?1")
    List<DiscountCategoryEntity> findByDiscountId(String id);

    @Query("select d from DiscountCategoryEntity d\n" +
            "left join CategoryEntity c on d.categoryId = c.id\n" +
            "where d.discountId = ?1 and c.active = true")
    List<DiscountCategoryEntity> findByDiscountIdAndCategoryActive(String id);
}
