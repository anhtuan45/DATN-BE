package com.ws.masterserver.excel.discount_revenue;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Arrays;
import java.util.List;

interface DiscountRevenueFunction {
    void setValue(Cell cell, DiscountRevenueDto dto);
}

@SuppressWarnings("deprecation")
public enum DiscountRevenueColumn implements DiscountRevenueFunction {
    NO("STT") {
        @Override
        public void setValue(Cell cell, DiscountRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getIndex());
        }
    },
    CODE("Mã khuyến mãi") {
        @Override
        public void setValue(Cell cell, DiscountRevenueDto dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getCode());
        }
    },
    STATUS("Trạng thái") {
        @Override
        public void setValue(Cell cell, DiscountRevenueDto dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getStatus());
        }
    },
    TIME("Thời gian diễn ra") {
        @Override
        public void setValue(Cell cell, DiscountRevenueDto dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getTime());

        }
    },
    REVENUE("Doanh thu") {
        @Override
        public void setValue(Cell cell, DiscountRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getRevenue());
        }
    };

    private String name;

    DiscountRevenueColumn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return ordinal();
    }

    private static final List<DiscountRevenueColumn> DISCOUNT_REVENUE_COLUMNS = Arrays.asList(values());

    public static List<DiscountRevenueColumn> getColumns() {
        return DISCOUNT_REVENUE_COLUMNS.subList(0, REVENUE.getIndex() + 1);
    }


}
