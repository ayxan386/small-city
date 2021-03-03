package com.jsimplec.places.util;

import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class ExcelUtils {

  private HSSFCellStyle commonStyle;
  private HSSFCellStyle emptyCellStyle;

  public <T extends TableDataExporter> Workbook exportToExcel(T data) {
    HSSFWorkbook workbook = new HSSFWorkbook();
    initCellStyles(workbook);
    return addNewSheet(data, workbook);
  }


  public <T extends TableDataExporter> Workbook addNewSheet(T data, Workbook workbook) {
    Sheet sheet = workbook.createSheet(data.getSheetName());

    addHeader(sheet, data.getColumnOrder());

    addTableData(data, sheet);

    return workbook;
  }

  private <T extends TableDataExporter> void addTableData(T data, Sheet sheet) {
    AtomicInteger rowNumber = new AtomicInteger(1);
    data
        .getData()
        .forEach(map -> {
          Row currentRow = sheet.createRow(rowNumber.getAndAdd(1));
          AtomicInteger columnNumber = new AtomicInteger(0);
          data
              .getColumnOrder()
              .forEach(fieldName -> {
                String stringValue = getStringValue(map, fieldName);
                setCell(currentRow, columnNumber, stringValue);
              });
        });
  }

  private String getStringValue(Map<String, Object> map, String fieldName) {
    Object value = map.getOrDefault(fieldName, "----");
    return ObjectUtils.nullSafeToString(value);
  }

  private void setCell(Row currentRow, AtomicInteger columnNumber, String stringValue) {
    Cell cell = currentRow.createCell(columnNumber.getAndAdd(1));
    cell.setCellStyle(stringValue.equals("----") ? emptyCellStyle : commonStyle);
    cell.setCellValue(stringValue);
  }

  private void addHeader(Sheet sheet, List<String> columnOrder) {
    Row headers = sheet.createRow(0);
    AtomicInteger index = new AtomicInteger(0);
    columnOrder.forEach(fieldName -> headers.createCell(index.getAndAdd(1)).setCellValue(fieldName));
  }

  private void initCellStyles(HSSFWorkbook workbook) {
    commonStyle = workbook.createCellStyle();
    commonStyle.setAlignment(HorizontalAlignment.RIGHT);
    emptyCellStyle = workbook.createCellStyle();
    emptyCellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
  }

}
