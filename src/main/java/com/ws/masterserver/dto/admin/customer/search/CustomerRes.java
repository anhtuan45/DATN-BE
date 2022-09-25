package com.ws.masterserver.dto.admin.customer.search;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomerRes {
    private String id;
    private String firstName;
    private String lastName;
    private String combinationName;
    private Date dob;
    private String dobValue;
    private Integer age;
    private Boolean gender;
    private String genderValue;
    private String email;
    private String phone;
    private Boolean active;
    private Date createdDate;
    private String createdDateValue;
    /**
     * số đơn đã đặt
     */
    private Long orderNumber;
    /**
     * đơn hàng gần đây nhat
     */
    private Date recentOrder;
    private String recentOrderValue;
}
