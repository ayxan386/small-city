package com.jsimplec.places.service.impl;

import com.jsimplec.places.dto.log.LogResponseDTO;
import com.jsimplec.places.mapper.ChangeLogMapper;
import com.jsimplec.places.repository.ChangeLogRepository;
import com.jsimplec.places.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ChangeLogServiceImpl implements ChangeLogService {

  private final ChangeLogMapper changeLogMapper;
  private final ChangeLogRepository logRepository;

  @Override
  public List<LogResponseDTO> getAll() {
    return logRepository
        .findAll(Sort.by(Sort.Direction.DESC, "date"))
        .stream()
        .map(changeLogMapper::mapToResponse)
        .collect(toList());
  }
}
