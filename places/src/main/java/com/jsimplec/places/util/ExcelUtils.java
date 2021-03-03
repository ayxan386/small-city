package com.jsimplec.places.util;

import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class ExcelUtils {

  public <T extends ExportableDto> Workbook exportToExcel(T data) {
    HSSFWorkbook workbook = new HSSFWorkbook();
    return addNewSheet(data, workbook);
  }

  public <T extends ExportableDto> Workbook addNewSheet(T data, Workbook workbook) {
    Sheet sheet = workbook.createSheet(data.getSheetName());

    addHeader(sheet, data.getColumnOrder());

    addTableData(data, sheet);

    return workbook;
  }

  private <T extends ExportableDto> void addTableData(T data, Sheet sheet) {
    AtomicInteger rowNumber = new AtomicInteger(1);
    Map<String, Integer> columnOrder = data.getColumnOrder();
    data
        .getData()
        .forEach(map -> {
          Row currentRow = sheet.createRow(rowNumber.getAndAdd(1));
          map
              .forEach((fieldName, value) -> {
                Integer columnIndex = columnOrder.get(fieldName);
                Cell cell = currentRow.createCell(columnIndex);
                cell.setCellValue(ObjectUtils.nullSafeToString(value));
              });
        });
  }

  private void addHeader(Sheet sheet, Map<String, Integer> columnOrder) {
    Row headers = sheet.createRow(0);
    columnOrder.forEach((fieldName, index) -> headers.createCell(index).setCellValue(fieldName));
  }

}
