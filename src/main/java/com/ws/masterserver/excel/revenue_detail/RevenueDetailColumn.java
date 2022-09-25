package com.ws.masterserver.excel.revenue_detail;

import com.ws.masterserver.dto.admin.report.revenue_detail.RevenueDetailItem;
import com.ws.masterserver.dto.admin.report.revenue_detail.RevenueDetailRes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Arrays;
import java.util.List;


interface RevenueDetailFunction {
    void setValue(Cell cell, RevenueDetailItem dto);
}

@SuppressWarnings("deprecation")
public enum RevenueDetailColumn implements RevenueDetailFunction {
    TIME("Thời gian") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getTime());
        }
    },
    ORDER_NUMBER("Số lượng đơn hàng") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getOrderNumber());
        }
    },
    SALE("Doanh thu") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getSale());
        }
    },
    DISCOUNT("Giảm giá") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getDiscount());
        }
    },
//    REFUND("Trả lại hàng") {
//        @Override
//        public void setValue(Cell cell, RevenueDetailItem dto) {
//            cell.setCellType(CellType.NUMERIC);
//            cell.setCellValue(dto.getRefund());
//        }
//    },
    NET_SALE("Doanh thu thực") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getNetSale());
        }
    },
    SHIP("Vận chuyển") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getShip());
        }
    },
    TOTAL("Tổng doanh thu") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getTotal());
        }
    },
    PRODUCT_ORDER_NUMBER("Số lượng đặt hàng") {
        @Override
        public void setValue(Cell cell, RevenueDetailItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getProductOrderNumber());
        }
    },
//    PRODUCT_REFUND_NUMBER("Số lượng trả lại") {
//        @Override
//        public void setValue(Cell cell, RevenueDetailItem dto) {
//            cell.setCellType(CellType.NUMERIC);
//            cell.setCellValue(dto.getProductRefundNumber());
//        }
//    },
//    PRODUCT_NUMBER("Số lượng thực") {
//        @Override
//        public void setValue(Cell cell, RevenueDetailItem dto) {
//            cell.setCellType(CellType.NUMERIC);
//            cell.setCellValue(dto.getProductNumber());
//        }
//    };
;
    private final String name;

    RevenueDetailColumn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return ordinal();
    }

    private static final List<RevenueDetailColumn> REVENUE_DETAIL_COLUMNS = Arrays.asList(values());

    public static List<RevenueDetailColumn> getRevenueDetailColumns() {
        return REVENUE_DETAIL_COLUMNS.subList(TIME.getIndex(), PRODUCT_ORDER_NUMBER.getIndex());
    }

}
