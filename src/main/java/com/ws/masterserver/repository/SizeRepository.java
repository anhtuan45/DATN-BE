package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.entity.SizeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, String> {
    SizeEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);
    @Query("select distinct s.name\n" +
            "from SizeEntity s\n" +
            "left join ProductOptionEntity po on po.sizeId = s.id\n" +
            "where po.productId = ?1")
    List<String> findByProductId(String productId);

    String findNameById(String sizeId);

    @Query("select distinct s.name\n" +
            "from SizeEntity s\n" +
            "left join ProductOptionEntity po on po.sizeId = s.id\n" +
            "where po.productId = ?1")
    List<String> findDistinctByProductId(String productId);


    @Query("select DISTINCT new com.ws.masterserver.dto.customer.size.response.SizeResponse(" +
            "s.id,\n" +
            "s.name)\n" +
            "from SizeEntity s")
    List<SizeResponse> getAllSize();


//    ===========

    @Query("select s.name from SizeEntity s where s.name=?1")
    Boolean findByName(String name);

//    @Query("select s.code from SizeEntity s where s.code=?1")
//    Boolean findByCode(String code);

//    @Query(value = "select new com.ws.masterserver.dto.admin.size.SizeResponseV2(\n" +
//            "c.id,\n" +
//            "c.name,\n" +
//            "c.code,\n" +
//            "c.active,\n" +
//            "c.createdDate)\n" +
//            "from SizeEntity c order by c.createdDate")
//    List<SizeResponseV2> findAllSizeV2();

    @Query("select s\n" +
            "from SizeEntity s\n" +
            "where (:textSearch is null or upper(unaccent(s.name)) like concat('%', unaccent(:textSearch), '%'))\n" +
            "and (:active is null or s.active = :active)")
    Page<SizeEntity> search(@Param("textSearch") String textSearch,
                            @Param("active") Boolean active,
                            Pageable pageable);

    @Query("select (count(s) > 0) from SizeEntity s where upper(s.name) = upper(?1)")
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String trim, String id);
}
