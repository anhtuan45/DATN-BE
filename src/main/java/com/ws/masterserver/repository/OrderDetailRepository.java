package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.order.detail.ItemDto;
import com.ws.masterserver.dto.customer.order.OrderDetailRes;
import com.ws.masterserver.dto.customer.order.ProductInOrderDetail;
import com.ws.masterserver.dto.customer.order.order_detail.OrderDetailResponse;
import com.ws.masterserver.dto.customer.order.order_detail.ProductOrderDetail;
import com.ws.masterserver.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, String> {

    @Query("select new com.ws.masterserver.dto.admin.order.detail.ItemDto(\n" +
            "product.id,\n" +
            "product.name,\n" +
            "color.name,\n" +
            "sizes.name,\n" +
            "productOption.image,\n" +
            "material.name,\n" +
            "category.name,\n" +
            "coalesce(orderDetail.qty, 0) ,\n" +
            "coalesce(orderDetail.price, 0) ,\n" +
            "coalesce(orderDetail.discount, 0) ,\n" +
            "orderDetail.qty * orderDetail.price - coalesce(orderDetail.discount, 0))\n" +
            "from OrderDetailEntity orderDetail\n" +
            "left join ProductOptionEntity productOption on productOption.id = orderDetail.productOptionId\n" +
            "left join ProductEntity product on product.id = productOption.productId\n" +
            "left join ColorEntity color on color.id = productOption.colorId\n" +
            "left join SizeEntity sizes on sizes.id = productOption.sizeId\n" +
            "left join CategoryEntity category on category.id = product.categoryId\n" +
            "left join MaterialEntity material on material.id = product.materialId\n" +
            "where orderDetail.orderId = :orderId")
    List<ItemDto> getOrderDetailItemByOrderId(@Param("orderId") String orderId);


    @Query("select new com.ws.masterserver.dto.customer.order.ProductInOrderDetail(\n" +
            "p.name,\n" +
            "po.id,\n" +
            "p.id,\n" +
            "po.image,\n" +
            "s.name,\n" +
            "c.name,\n" +
            "r.id,\n" +
            "od.price,\n" +
            "od.qty,\n" +
            "od.qty * od.price)\n" +
            "from OrderDetailEntity od\n" +
            "left join OrderEntity o on od.orderId = o.id\n" +
            "left join ProductOptionEntity po on po.id = od.productOptionId\n" +
            "left join ProductEntity p on p.id = po.productId\n" +
            "left join ReviewEntity r on r.productId = p.id\n" +
            "left join ColorEntity c on c.id = po.colorId\n" +
            "left join SizeEntity s on s.id = po.sizeId\n" +
            "where od.orderId = :orderId and o.userId = :userId")
    List<ProductInOrderDetail> getProductList(@Param("orderId") String orderId,@Param("userId") String userId);

    @Query("select DISTINCT new com.ws.masterserver.dto.customer.order.OrderDetailRes(" +
            "a.nameOfRecipient,\n" +
            "a.phoneNumber,\n" +
            "o.note,\n" +
            "o.code,\n" +
            "o.createdDate,\n" +
            "o.total,\n" +
            "o.shipPrice,\n" +
            "a.combination,\n" +
            "o.payment,\n" +
            "o.payed,\n" +
            "o.shipPriceDiscount,\n" +
            "o.shopPriceDiscount,\n" +
            "o.status)\n" +
            "from OrderDetailEntity od\n" +
            "LEFT JOIN OrderEntity o on od.orderId = o.id\n" +
            "LEFT JOIN AddressEntity a on o.addressId = a.id\n" +
            "where od.orderId = :orderId")
    OrderDetailRes getOrderDetail(@Param("orderId") String orderId);


    @Query("select DISTINCT new com.ws.masterserver.dto.customer.order.order_detail.ProductOrderDetail(" +
            "od.productOptionId,\n" +
            "od.qty)\n" +
            "from OrderDetailEntity od\n" +
            "LEFT JOIN OrderEntity o on od.orderId = o.id\n" +
            "where od.orderId = :orderId")
    List<ProductOrderDetail> getProductOrder(@Param("orderId") String orderId);

    @Query(value = "SELECT * FROM order_detail od WHERE od.order_id = ?1",nativeQuery = true)
    OrderDetailEntity findByOrderId(String orderId);

    @Query("select DISTINCT new com.ws.masterserver.dto.customer.order.order_detail.OrderDetailResponse(" +
            "p.id,\n" +
            "od.productOptionId,\n" +
            "o.id,\n" +
            "o.status)\n" +
            "from OrderDetailEntity od\n" +
            "LEFT JOIN OrderEntity o on od.orderId = o.id\n" +
            "LEFT JOIN ProductOptionEntity po on od.productOptionId = po.id\n" +
            "LEFT JOIN ProductEntity p on po.productId = p.id\n" +
            "where o.userId = :userId and p.id = :productId")
    OrderDetailResponse findByUserIdAndProductId(@Param("userId") String userId, @Param("productId") String productId);


    @Query("select DISTINCT new com.ws.masterserver.dto.customer.order.order_detail.OrderDetailResponse(" +
            "p.id,\n" +
            "od.productOptionId,\n" +
            "o.id,\n" +
            "o.status)\n" +
            "from OrderDetailEntity od\n" +
            "LEFT JOIN OrderEntity o on od.orderId = o.id\n" +
            "LEFT JOIN ProductOptionEntity po on od.productOptionId = po.id\n" +
            "LEFT JOIN ProductEntity p on po.productId = p.id\n" +
            "where o.userId = :userId and p.id = :productId and o.id = :orderId and o.status='RECEIVED'")
    List<OrderDetailResponse> checkConditionReview(@Param("userId") String userId, @Param("productId") String productId , @Param("orderId") String orderId);

//    @Query(value = "SELECT distinct on (p.id) p.id AS productId, po.id AS productOptionId,o.id AS orderId,o.status AS status \n" +
//            "from order_detail od \n" +
//            "join orders o on od.order_id = o.id \n" +
//            "join product_option po on od.product_option_id = po.id \n" +
//            "join product p on po.product_id = p.id \n" +
//            "where o.user_id = ?1 and p.id = ?2 and o.id = ?3 and o.status = 'RECEIVED'",nativeQuery = true)
//    OrderDetailResponse checkConditionReviews(String userId, String productId , String orderId);
}
