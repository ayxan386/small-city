package com.jsimplec.places.service;

import com.jsimplec.places.dto.log.LogResponseDTO;

import java.util.List;

public interface ChangeLogService {
  List<LogResponseDTO> getAll();
}
