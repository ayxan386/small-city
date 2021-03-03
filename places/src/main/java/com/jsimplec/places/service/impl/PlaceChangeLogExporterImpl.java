package com.jsimplec.places.service.impl;

import com.jsimplec.places.service.PlaceChangeLogExporter;
import com.jsimplec.places.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceChangeLogExporterImpl implements PlaceChangeLogExporter {

  private final ExcelUtils excelUtils;
  private final ChangeLogServiceImpl changeLogService;

  @Override
  public Workbook getAllLogs() {
    return excelUtils.exportToExcel(this);
  }

  @Override
  public Map<String, Integer> getColumnOrder() {
    return Map.of("id", 0,
        "date", 1,
        "entityId", 2,
        "name", 3,
        "description", 4,
        "rating", 5);
  }

  @Override
  public String getSheetName() {
    return "Places log";
  }

  @Override
  public List<Map<String, Object>> getData() {
    return changeLogService
        .getAll()
        .stream()
        .map(logRes -> {
          HashMap<String, Object> data = new HashMap<>(logRes.getChangedFields());
          data.put("id", logRes.getId());
          data.put("entityId", logRes.getEntityId());
          data.put("date", logRes.getDate());
          return data;
        })
        .collect(Collectors.toList());
  }
}
