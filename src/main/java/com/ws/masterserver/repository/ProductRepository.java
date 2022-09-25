package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.product.ProductBestSeller;
import com.ws.masterserver.dto.customer.product.ProductRelatedRes;
import com.ws.masterserver.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByCategoryIdAndActive(String productId, Boolean active);

    @Query("select count(od.productOptionId)\n" +
            "from OrderDetailEntity od\n" +
            "left join OrderEntity o on o.id = od.orderId and o.payed = true\n" +
            "left join ProductOptionEntity po on po.id = od.productOptionId\n" +
            "left join ProductEntity p on p.id = po.productId and p.active = true\n" +
            "where p.id = :id\n" +
            "group by p.id")
    Optional<Long> countSellNumber(@Param("id") String id);


    @Query(value = "select distinct on (p.id) p.id AS productId,p.name AS productName,po.price AS price,po.image AS image \n" +
            "from product p \n" +
            "join category c on p.category_id = c.id \n" +
            "join product_option po on p.id = po.product_id \n" +
            "where p.category_id = ?1",nativeQuery = true)
    List<ProductRelatedRes> getProductRelated(String categoryId);

    @Modifying
    @Query(value = "update product\n" +
            "set view_number = view_number + 1\n" +
            "where id = ?1", nativeQuery = true)
    void increaseViewNumberByProductOptionId(String id);

    @Query("select p from ProductEntity p where p.active = true order by p.name")
    List<ProductEntity> findAllOrderByName();

    @Query("select count(p) from ProductEntity p where p.categoryId = ?1")
    Long countByCategoryId(String categoryId);

    @Query("select p from ProductEntity p where p.active = ?1")
    List<ProductEntity> findAllByActive(Boolean active);

    @Query("select p from ProductEntity p where p.id = ?1 and p.active = ?2")
    ProductEntity findByIdAndActive(String productId, boolean b);

    @Query("select p from ProductEntity p where p.categoryId is null or p.categoryId = '' order by p.name")
    List<ProductEntity> find4_0Category();

    @Query("select p from ProductEntity p\n" +
            "where p.categoryId = ?1 or p.categoryId is null or p.categoryId = ''")
    List<ProductEntity> find4_0CategoryUpdate(String id);

    @Query(value = "select p.id,p.name,po.image ,po.price\n" +
            "from orders o \n" +
            "join order_detail od on od.order_id = o.id \n" +
            "join product_option po on od.product_option_id = po.id \n" +
            "join product p on po.product_id = p.id \n" +
            "where o.status = 'RECEIVED'\n" +
            "group  by p.id,po.image ,po.price\n" +
            "order by sum(od.qty) desc",nativeQuery = true)
    List<ProductBestSeller> getProductBestSellers();

    @Query(value = "select distinct on (p.id) p.id AS productId,p.name AS name ,po.image AS image,po.price AS price \n" +
            "from product_option po\n" +
            "join product p on p.id = po.product_id \n" +
            "limit 8",nativeQuery = true)
    List<ProductBestSeller> testGet8Rows();

    @Query("select p from ProductEntity p where upper(p.name) = upper(?1)")
    ProductEntity findByName(String name);

    @Query("select p from ProductEntity p where p.categoryId = ?1")
    List<ProductEntity> findByCategoryId(String id);

    @Query("select (count(p.id) > 0) from ProductEntity p where upper(p.name) = upper(?1) and p.id <> ?2")
    boolean checkDuplicateName4Update(String name, String id);

    @Query("select p from ProductEntity p where p.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_READ)
    ProductEntity findByIdUpdate(String id);
}
