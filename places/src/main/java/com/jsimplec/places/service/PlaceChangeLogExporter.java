package com.jsimplec.places.service;

import com.jsimplec.places.util.TableDataExporter;
import org.apache.poi.ss.usermodel.Workbook;

public interface PlaceChangeLogExporter extends TableDataExporter {
    Workbook getAllLogs();
}
