package com.ws.masterserver.excel.user_detail;

import com.ws.masterserver.dto.admin.report.user_detail.UserDetailReportItem;
import com.ws.masterserver.dto.admin.report.user_detail.UserDetailReportRes;
import com.ws.masterserver.excel.ExcelBase;
import com.ws.masterserver.excel.revenue_detail.RevenueDetailColumn;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.constants.WsCode;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author myname
 */
public class UserDetailExcel {
    private static final String SHEET_NAME = "Báo cáo khách hàng";
    private static final Integer TITLE_ROW_NUMBER = 0;
    private static final Integer START_TITLE_ROW_NUMBER = 2;
    private static final Integer END_TITLE_ROW_NUMBER = 3;
    private static final String START_TITLE = "Từ ngày:";
    private static final String END_TITLE = "Đến ngày:";
    private static final Integer HEADER_ROW_NUMBER = 5;

    public static Object export(UserDetailReportRes res) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (Workbook workbook = new HSSFWorkbook()) {
            List<UserDetailColumn> detailColumns = UserDetailColumn.getUserDetailColumns();

            Font boldFont = ExcelBase.getFont(workbook, true, false, (short) 10);
            Font italicFont = ExcelBase.getFont(workbook, false, true, (short) 10);

            CellStyle italicCellStyle = workbook.createCellStyle();
            italicCellStyle.setFont(italicFont);

            Sheet sheet = workbook.createSheet(SHEET_NAME);

            String startStr = DateUtils.toStr(res.getStart(), DateUtils.F_DDMMYYYY);
            String endStr = DateUtils.toStr(res.getEnd(), DateUtils.F_DDMMYYYY);

            createTitleRow(workbook, sheet, boldFont, detailColumns.size() - 1);
            createTimeRow(sheet, italicCellStyle, detailColumns.size(), START_TITLE_ROW_NUMBER, START_TITLE, startStr);
            createTimeRow(sheet, italicCellStyle, detailColumns.size(), END_TITLE_ROW_NUMBER, END_TITLE, endStr);
            createHeaderRow(workbook, sheet, boldFont, detailColumns);
            createBodyRow(workbook, sheet, detailColumns, res.getData());

            workbook.write(bos);
            bos.close();
        } catch (Exception e) {
            throw new WsException(WsCode.EXPORT_FAILED);
        }
        return bos.toByteArray();    }

    private static void createBodyRow(Workbook workbook, Sheet sheet, List<UserDetailColumn> detailColumns, List<UserDetailReportItem> data) {
        CellStyle cellStyle = ExcelBase.getBorderCellStyle(workbook);
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(HEADER_ROW_NUMBER + i + 1);
            UserDetailReportItem dto = data.get(i);
            for (int j = 0; j < detailColumns.size(); j++) {
                UserDetailColumn column = detailColumns.get(j);
                Cell cell = row.createCell(column.getIndex());
                cell.setCellStyle(cellStyle);
                column.setValue(cell, dto);
                sheet.autoSizeColumn(j);
            }
            sheet.autoSizeColumn(i);
        }
    }

    private static void createHeaderRow(Workbook workbook, Sheet sheet, Font fond, List<UserDetailColumn> detailColumns) {
        Row row = sheet.createRow(HEADER_ROW_NUMBER);
        CellStyle cellStyle = ExcelBase.getBorderCellStyle(workbook);
        cellStyle.setFont(fond);
        for (UserDetailColumn column : detailColumns) {
            Cell cell = row.createCell(column.getIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(column.getName());
            sheet.autoSizeColumn(column.getIndex());
        }
    }

    private static void createTimeRow(Sheet sheet, CellStyle cellStyle, int size, Integer rowNumber, String title, String value) {
        Row timeRow = sheet.createRow(rowNumber);
        Cell timeTitleCell = timeRow.createCell(size - 2);
        timeTitleCell.setCellStyle(cellStyle);
        timeTitleCell.setCellValue(title);
        sheet.autoSizeColumn(size - 2);
        Cell timeValueCell = timeRow.createCell(size - 1);
        timeValueCell.setCellStyle(cellStyle);
        timeValueCell.setCellValue(value);
        sheet.autoSizeColumn(size - 1);
    }

    private static void createTitleRow(Workbook workbook, Sheet sheet, Font boldFont, int lastCol) {
        Row row = sheet.createRow(TITLE_ROW_NUMBER);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, lastCol));
        CellStyle cellStyle = ExcelBase.getVerticalCellStyle(workbook, boldFont);
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(SHEET_NAME);
    }

}
