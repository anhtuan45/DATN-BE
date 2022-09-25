package com.ws.masterserver.repository;

import com.ws.masterserver.entity.SuggestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestRepository extends JpaRepository<SuggestEntity, String> {

    SuggestEntity findByBodyWeightIdAndCategoryIdIsNull(String weightId);

    SuggestEntity findByBodyWeightIdAndBodyHeightIdAndCategoryIdIsNull(String weightId, String heightId);

    SuggestEntity findByBodyHeightIdAndCategoryIdIsNull(String heightId);

    SuggestEntity findByBodyWeightIdAndCategoryId(String id, String categoryId);

    SuggestEntity findByBodyWeightIdAndBodyHeightIdAndCategoryId(String id, String id1, String categoryId);

    SuggestEntity findByBodyHeightIdAndCategoryId(String id, String categoryId);
}
