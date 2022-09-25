package com.ws.masterserver.dto.admin.discount.detail;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class DiscountDetailResponse {
    private Object id;
    private Object code;
    private Object des;
    private Object type;
    private Object typeName;
    private Object typeValue;
    private Object applyType;
    private Object applyTypeName;
    private Object applyTypeValue;
    private Object prerequisiteType;
    private Object prerequisiteTypeName;
    private Object prerequisiteTypeValue;
    private Object customerType;
    private Object customerTypeName;
    private Object customerTypeValue;
    private Object usageNumber;
    private Object usageLimit;
    private Object oncePerCustomer;
    private Object oncePerCustomerDetail;
    private Object startDate;
    private Object startDateFmt;
    private Object startDateMils;
    private Object hasEndDate;
    private Object endDate;
    private Object endDateFmt;
    private Object endDateMils;
    private Object status;
    private Object statusName;
    private Object canEdit;
}
