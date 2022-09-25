package com.ws.masterserver.dto.customer.order.order_detail;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

     private String productId;
     private String productOptionId;
     private String orderId;
     private String status;


//     String getProductId();
//
//     String getProductOptionId();
//
//     String getOrderId() ;
//
//     String getStatus() ;
}
