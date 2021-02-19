package com.jsimplec.places.controller;

import com.jsimplec.places.dto.log.LogResponseDTO;
import com.jsimplec.places.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class ChangeLogController {

  private final ChangeLogService repository;

  @GetMapping
  public List<LogResponseDTO> getAll() {
    return repository.getAll();
  }

}
