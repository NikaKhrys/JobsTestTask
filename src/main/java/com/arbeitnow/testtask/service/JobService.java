package com.arbeitnow.testtask.service;

import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for retrieving Job data from the DB
 *
 * @author NikaKhrys
 */
public interface JobService {

  /**
   * Retrieves all jobs with paging and sorting
   *
   * @param pageable - number and size of the page and sorting criteria
   * @return page with found jobs
   */
  Page<JobDto> getAll(Pageable pageable);

  /**
   * Retrieves top ten newest jobs
   *
   * @return list with found jobs
   */
  List<JobDto> getTopTen();

  /**
   * Retrieves statistics with jobs number for each location
   *
   * @return map with location as a key and jobs number as a value
   */
  Map<String, Long> getStatisticsForLocations();
}
