package com.ws.masterserver.excel.customer_revenue;


import com.ws.masterserver.excel.product_revenue.ProductRevenueColumn;
import com.ws.masterserver.excel.product_revenue.ProductRevenueDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Arrays;
import java.util.List;

interface CustomerRevenueFunction {
    void setValue(Cell cell, CustomerRevenueDto dto);
}

@SuppressWarnings("deprecation")
public enum CustomerRevenueColumn implements CustomerRevenueFunction {
    NO("STT"){
        @Override
        public void setValue(Cell cell, CustomerRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getIndex());
        }
    },
    NAME("Tên") {
        @Override
        public void setValue(Cell cell, CustomerRevenueDto dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getFullName());
        }
    },

    PHONE("Số điện thoại") {
        @Override
        public void setValue(Cell cell, CustomerRevenueDto dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getPhone());
        }
    },

    PURCHASES("Số lượt mua") {
        @Override
        public void setValue(Cell cell, CustomerRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getPurchases());
        }
    },

    TURNOVER("Doanh thu") {
        @Override
        public void setValue(Cell cell, CustomerRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getTurnover());
        }
    },

    ;


    private String name;

    CustomerRevenueColumn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return ordinal();
    }

    private static final List<CustomerRevenueColumn> CUSTOMER_REVENUE_COLUMNS = Arrays.asList(values());

    public static List<CustomerRevenueColumn> getColumns() {
        return CUSTOMER_REVENUE_COLUMNS.subList(NO.getIndex(), TURNOVER.getIndex() + 1);
    }
}
