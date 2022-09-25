package com.ws.masterserver.dto.admin.dashboard;

import lombok.Data;

@Data
public class ReportDto {
    /**
     * đơn hàng đang chờ xử lý
     */
    private Object pending;

    /**
     * người dùng mới
     */
    private Object user;

    /**
     * Doanh thu hôm nay
     */
    private Object today;

    /**
     * Doanh thu trong tuần
     */
    private Object week;

    /**
     * đơn hàng bị hủy
     */
    private Object cancel;
}
