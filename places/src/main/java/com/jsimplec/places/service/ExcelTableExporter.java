package com.jsimplec.places.service;

import com.jsimplec.places.util.TableDataExporter;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelTableExporter extends TableDataExporter {
  Workbook exportAll();

  String getFileName();
}
