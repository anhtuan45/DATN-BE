package com.ws.masterserver.dto.admin.order.detail;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailRes {
    private Object id;
    //mã DH
    private Object code;

    //Ghi chú
    private Object note;

    //code phương thức thanh toan
    private Object paymentCode;
    //tên phương thức thanh toan
    private Object paymentName;

    //phương thức vận chuyển
    private Object shipMethod;

    //đã thanh toán chưa
    private Object payed;

    //địa chỉ
    private Object addressCombination;

    //tên KH
    private Object customerId;
    private Object customerName;

    //SDT nhận hàng
    private Object phone;

    //tiền ship
    private Object shipPrice;
    private Object shipPriceFmt;

    //giảm giá tiền ship
    private Object shipDiscount;
    private Object shipDiscountFmt;

    //tổng giá tiền ship
    private Object shipTotal;
    private Object shipTotalFmt;

    //danh sách sản phẩm
    private Object items;

    //mã Khuyến mãi
    private Object discountId;
    private Object discountCode;
    private Object discountDes;

    //Lịch sử đơn hàng
    private Object history;

    //tổng giá tạm tính
    private Object totalPriceItems;
    private Object totalPriceItemsFmt;

    //tổng giảm giá
    private Object totalDiscountItems;
    private Object totalDiscountItemsFmt;

    //tổng tiền
    private Object totalItems;
    private Object totalItemsFmt;

    //tổng số lượng
    private Object totalQtyItems;

    //trạng thái hiện tại
    private Object status;
    private Object statusName;

    //tên người nhận
    private Object nameOfRecipient;

    //tổng giá
    private Object total;
    private Object totalFmt;
}
