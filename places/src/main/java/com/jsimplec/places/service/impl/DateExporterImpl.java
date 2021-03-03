package com.jsimplec.places.service.impl;

import com.jsimplec.places.service.ExcelTableExporter;
import com.jsimplec.places.service.PlaceChangeLogExporter;
import com.jsimplec.places.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DateExporterImpl implements ExcelTableExporter {
  private final PlaceChangeLogExporter placeLog;
  private final ExcelUtils excelUtils;

  @Override
  public List<String> getColumnOrder() {
    return List.of("id", "date");
  }

  @Override
  public String getSheetName() {
    return "static";
  }

  @Override
  public List<Map<String, Object>> getData() {
    return List.of(
        Map.of("id", 1, "date", "yesterday"),
        Map.of("id", 2, "date", "today"),
        Map.of("id", 3, "date", "tomorrow")
    );
  }

  @Override
  public Workbook exportAll() {
    return excelUtils.addNewSheet(this, placeLog.getAllLogs());
  }

  @Override
  public String getFileName() {
    return String.format("Date_only_%s.xslx", LocalDateTime.now().toString());
  }
}
