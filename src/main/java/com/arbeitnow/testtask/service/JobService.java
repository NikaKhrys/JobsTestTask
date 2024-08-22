package com.arbeitnow.testtask.service;

import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
  Page<JobDto> getAll(Pageable pageable);

  List<JobDto> getTopTen();

  Map<String, Long> getStatisticsForLocations();
}
