package com.jsimplec.places.util;

import java.util.List;
import java.util.Map;

public interface ExportableDto {
  Map<String, Integer> getColumnOrder();

  String getSheetName();

  List<Map<String, Object>> getData();
}
