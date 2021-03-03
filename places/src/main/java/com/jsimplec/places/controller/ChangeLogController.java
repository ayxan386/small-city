package com.jsimplec.places.controller;

import com.jsimplec.places.dto.log.LogResponseDTO;
import com.jsimplec.places.service.ChangeLogService;
import com.jsimplec.places.service.impl.DateExporterImpl;
import com.jsimplec.places.service.impl.PlaceChangeLogExporterImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class ChangeLogController {

  private final ChangeLogService repository;
  private final PlaceChangeLogExporterImpl logExporter;
  private final DateExporterImpl dateExporter;

  @GetMapping
  public List<LogResponseDTO> getAll() {
    return repository.getAll();
  }

  @GetMapping("/excel")
  public ResponseEntity<StreamingResponseBody> getAllExcel() {
    String fileName = logExporter.getFileName();
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline;filename=\"%s\"", fileName))
        .body(s -> logExporter.exportAll().write(s));
  }

  @GetMapping("/excel/2")
  public ResponseEntity<StreamingResponseBody> getAllExcel2ndPage() {
    String fileName = dateExporter.getFileName();
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline;filename=\"%s\"", fileName))
        .body(s -> dateExporter.exportAll().write(s));
  }

}
