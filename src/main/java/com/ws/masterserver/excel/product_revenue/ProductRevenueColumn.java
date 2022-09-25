package com.ws.masterserver.excel.product_revenue;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Arrays;
import java.util.List;

interface ProductRevenueFunction {
    void setValue(Cell cell, ProductRevenueDto dto);
}

@SuppressWarnings("deprecation")
public enum ProductRevenueColumn implements ProductRevenueFunction {
    NO("STT") {
        @Override
        public void setValue(Cell cell, ProductRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getIndex());
        }
    },
    NAME("Tên") {
        @Override
        public void setValue(Cell cell, ProductRevenueDto dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getName());
        }
    },
    CATEGORY_NAME("Danh mục") {
        @Override
        public void setValue(Cell cell, ProductRevenueDto dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getCategoryName());
        }
    },
    OPTION_NUMBER("Số loại") {
        @Override
        public void setValue(Cell cell, ProductRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getOptionNumber());
        }
    },
    REVENUE("Doanh thu") {
        @Override
        public void setValue(Cell cell, ProductRevenueDto dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getRevenue());
        }
    };

    private String name;

    ProductRevenueColumn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return ordinal();
    }

    private static final List<ProductRevenueColumn> PRODUCT_REVENUE_COLUMNS = Arrays.asList(values());

    public static List<ProductRevenueColumn> getColumns() {
        return PRODUCT_REVENUE_COLUMNS.subList(NO.getIndex(), REVENUE.getIndex() + 1);
    }
}
