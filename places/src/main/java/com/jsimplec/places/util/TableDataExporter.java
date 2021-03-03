package com.jsimplec.places.util;

import java.util.List;
import java.util.Map;

public interface TableDataExporter {
  List<String> getColumnOrder();

  String getSheetName();

  List<Map<String, Object>> getData();
}
