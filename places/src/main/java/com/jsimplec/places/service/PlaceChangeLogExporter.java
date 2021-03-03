package com.jsimplec.places.service;

import com.jsimplec.places.util.ExportableDto;
import org.apache.poi.ss.usermodel.Workbook;

public interface PlaceChangeLogExporter extends ExportableDto {
    Workbook getAllLogs();
}
