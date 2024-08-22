package com.arbeitnow.testtask.service;

import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;

/**
 * Service interface that retrieves jobs data from the external api
 *
 * @author NikaKhrys
 */
public interface JobsRetrievalService {

  /**
   * Retrieves given number of pages with jobs
   *
   * @param pageNumber - number of pages to be retrieved
   * @return list with found jobs
   */
  List<JobDto> getJobsFromApi(int pageNumber);
}
