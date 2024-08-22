package com.arbeitnow.testtask.service;

import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;

public interface JobsRetrievalService {

  List<JobDto> getJobsFromApi(int pageNumber);
}
