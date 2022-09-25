package com.ws.masterserver.excel.user_detail;

import com.ws.masterserver.dto.admin.report.user_detail.UserDetailReportItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
interface UserDetailFunction {
    void setValue(Cell cell, UserDetailReportItem dto);
}

//abc
@SuppressWarnings("deprecation")
public enum UserDetailColumn implements UserDetailFunction {
    TIME("Thời gian") {
        @Override
        public void setValue(Cell cell, UserDetailReportItem dto) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(dto.getTime());
        }
    },
    TOTAL("Số lượng khách hàng mới") {
        @Override
        public void setValue(Cell cell, UserDetailReportItem dto) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(dto.getTotal());
        }
    };

    private final String name;

    UserDetailColumn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return ordinal();
    }

    private static final List<UserDetailColumn> USER_DETAIL_COLUMNS = Arrays.asList(values());

    public static List<UserDetailColumn> getUserDetailColumns() {
        return USER_DETAIL_COLUMNS.subList(TIME.getIndex(), TOTAL.getIndex() + 1);
    }
}
