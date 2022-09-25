package com.ws.masterserver.excel.product_revenue;

import com.ws.masterserver.dto.admin.report.product.ProductRevenueRes;
import com.ws.masterserver.excel.ExcelBase;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ProductRevenueExcel {

    private static final String SHEET_NAME = "Doanh thu theo sản phẩm";
    private static final Integer HEADER_ROW_NUMBER = 0;

    public static Object export(List<ProductRevenueRes> data) {
        log.info("export() start with payload: {}", JsonUtils.toJson(data));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (Workbook workbook = new HSSFWorkbook()) {
            List<ProductRevenueColumn> exportColumns = ProductRevenueColumn.getColumns();
            Font boldFont = ExcelBase.getFont(workbook, true, false, (short) 10);

            Sheet sheet = workbook.createSheet(SHEET_NAME);

            createHeaderRow(workbook, sheet, boldFont, exportColumns);
            createBodyRows(workbook, sheet, exportColumns, data);
            workbook.write(bos);
            bos.close();
        } catch (Exception e) {
            throw new WsException(WsCode.EXPORT_FAILED);
        }
        return bos.toByteArray();
    }

    private static void createBodyRows(Workbook workbook, Sheet sheet, List<ProductRevenueColumn> exportColumns, List<ProductRevenueRes> productRevenueRes) {
        AtomicInteger index = new AtomicInteger(1);
        List<ProductRevenueDto> productRevenueDtos = productRevenueRes
                .stream()
                .map(o -> ProductRevenueDto.builder()
                        .index(index.getAndIncrement())
                        .id(o.getId())
                        .name(o.getName())
                        .categoryName(o.getCategoryName())
                        .optionNumber(o.getOptionNumber())
                        .revenue(o.getRevenue())
                        .build()).collect(Collectors.toList());
        CellStyle cellStyle = ExcelBase.getBorderCellStyle(workbook);
        for (int i = 0; i < productRevenueDtos.size(); i++) {
            Row row = sheet.createRow(HEADER_ROW_NUMBER + i + 1);
            ProductRevenueDto dto = productRevenueDtos.get(i);
            for (ProductRevenueColumn column : exportColumns) {
                sheet.autoSizeColumn(column.getIndex());
                Cell cell = row.createCell(column.getIndex());
                cell.setCellStyle(cellStyle);
                column.setValue(cell, dto);
            }
        }
    }

    private static void createHeaderRow(Workbook workbook, Sheet sheet, Font boldFont, List<ProductRevenueColumn> exportColumns) {
        Row headerRow = sheet.createRow(HEADER_ROW_NUMBER);
        CellStyle borderCellStyle = ExcelBase.getBorderCellStyle(workbook);
        borderCellStyle.setFont(boldFont);
        exportColumns.forEach(export -> {
            Cell cell = headerRow.createCell(export.getIndex());
            cell.setCellStyle(borderCellStyle);
            cell.setCellValue(export.getName());
            sheet.autoSizeColumn(export.getIndex());
        });
    }
}
