package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryEntity {
    @Id
    private String id;

    //Mã giao dịch
    private String transactionNo;

    // số hóa đơn
    private String invoiceNumber;

    //Mã ngân hàng
    private String bankCode;

    //Nội dung thanh toán
    private String orderInfo;

    //số Tiền
    private int amount;

    //Trạng thái giao dịch
    private String status;

    //Ngày tạo
    private String createDate;

    //Mã đơn hàng
    private String orderId;
}
