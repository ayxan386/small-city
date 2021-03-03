package com.jsimplec.places.controller;

import com.jsimplec.places.dto.log.LogResponseDTO;
import com.jsimplec.places.service.ChangeLogService;
import com.jsimplec.places.service.PlaceChangeLogExporter;
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
  private final PlaceChangeLogExporter logExporter;

  @GetMapping
  public List<LogResponseDTO> getAll() {
    return repository.getAll();
  }

  @GetMapping("/excel")
  public ResponseEntity<StreamingResponseBody> getAllExcel() {
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"myfilename.xlsx\"")
        .body(s -> logExporter.getAllLogs().write(s));
  }

}
