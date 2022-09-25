package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.category.CategoryRes;
import com.ws.masterserver.dto.customer.suggest.CategoryDto;
import com.ws.masterserver.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    CategoryEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);

    @Query("select (count(c) > 0) from CategoryEntity c where upper(c.name) = upper(?1) and c.active = ?2 and c.id <> ?3")
    Boolean existsByNameAndActiveAndIdNot(String trim, Boolean aTrue, String id);

    Boolean existsByNameIgnoreCaseAndActive(String toLowerCase, Boolean aTrue);

    CategoryEntity findByNameIgnoreCaseAndActive(String categoryName, Boolean aTrue);

    @Query("select new com.ws.masterserver.dto.customer.suggest.CategoryDto(\n" +
            "c.id,\n" +
            "c.name,\n" +
            "c.image) from CategoryEntity c\n" +
            "where c.active = true\n" +
            "order by c.name")
    List<CategoryDto> findSuggestCategories();

    @Query("select c from CategoryEntity c where c.active = true order by c.name")
    List<CategoryEntity> noPage();

    @Query("SELECT c FROM CategoryEntity c\n" +
            "WHERE (UNACCENT(UPPER(c.name)) LIKE CONCAT('%', UNACCENT(:textSearch), '%')\n" +
            "OR UNACCENT(UPPER(c.des)) LIKE CONCAT('%', UNACCENT(:textSearch), '%'))\n" +
            "AND (:active IS NULL OR c.active = :active)\n" +
            "AND (:typeId IS NULL OR c.typeId = :typeId)")
    Page<CategoryEntity> search(@Param("textSearch") String textSearch,
                                @Param("active") Boolean active,
                                @Param("typeId") String typeId,
                                Pageable pageable);

    @Query("select c from CategoryEntity c where c.active = ?1")
    List<CategoryEntity> findByActive(boolean b);


    @Query("select new com.ws.masterserver.dto.customer.category.CategoryRes(\n" +
            "c.id,\n" +
            "c.name,\n" +
            "c.slug) from CategoryEntity c\n" +
            "where c.active = true\n" +
            "order by c.name")
    List<CategoryRes> findAllCategoryAndSlug();

}
