package com.ws.masterserver.dto.customer.order.checkin;

import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckinResponse {
    /**
     * @Param fullAddress: Địa chỉ cụ thể khách hàng chọn giao
     * @Param createTime: Ngày tạo
     * @Param statusTime: thời gian cập nhật trạng thái trên
     * @Param customerId: mã khách hàng
     * @Param customerFullName: Tên khách hàng

     * */
    private String id;
    private String fullAddress;
    private String createTime;
    private String customerId;
    private String customerFullName;

    /**
     * Status hiện tại
     * @Param status: Giá trị hiện tại
     * @Param note: note
     * @Param statusTime: thời gian cập nhật trạng thái trên
     * */
    private StatusEnum status;
    private String note;
    private Long statusTime;

}
