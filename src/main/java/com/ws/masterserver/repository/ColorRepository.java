package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.color.ColorResponseV2;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.product.search.ColorDto;
import com.ws.masterserver.entity.ColorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author myname
 */
@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, String> {
    ColorEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);

    @Query("select c from ColorEntity c where c.name=?1")
    Boolean findByName(String name);

    @Query("select c from ColorEntity c where c.hex=?1")
    Boolean findByHex(String hex);

    @Query("select distinct c.name \n" +
            "from ColorEntity c\n" +
            "left join ProductOptionEntity po on po.colorId = c.id\n" +
            "where po.productId = ?1")
    List<String> findByProductId(String productId);

    @Query("select distinct c.name \n" +
            "from ColorEntity c\n" +
            "left join ProductOptionEntity po on po.colorId = c.id\n" +
            "where po.productId = ?1")
    List<String> findDistinctByProductId(String productId);

    @Query("select DISTINCT new com.ws.masterserver.dto.customer.product.ColorResponse(" +
            "c.id,\n" +
            "c.name)\n" +
            "from ColorEntity c")
    List<ColorResponse> findAllColor();


    @Query(value = "select new com.ws.masterserver.dto.admin.color.ColorResponseV2(\n" +
            "c.id,\n" +
            "c.name,\n" +
            "c.hex,\n" +
            "c.active,\n" +
            "c.createdDate)\n" +
            "from ColorEntity c order by c.createdDate")
    List<ColorResponseV2> findAllColorV2();

    @Query("select c from ColorEntity c where c.active = ?1")
    List<ColorEntity> findByActive(boolean b);

    @Query("SELECT c FROM ColorEntity c\n" +
            "WHERE \n" +
            "(:active IS NULL OR c.active = :active)\n" +
            "AND (UNACCENT(UPPER(c.name)) LIKE CONCAT('%', UNACCENT(:textSearch) , '%')\n" +
            "OR UNACCENT(UPPER(c.hex)) LIKE CONCAT('%', UNACCENT(:textSearch) , '%'))")
    Page<ColorEntity> searchColor(@Param("active") Boolean active,
                                  @Param("textSearch") String textSearch,
                                  Pageable pageable);


}
