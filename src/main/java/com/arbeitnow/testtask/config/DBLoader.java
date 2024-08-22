package com.arbeitnow.testtask.config;

import com.arbeitnow.testtask.mappers.JobMapper;
import com.arbeitnow.testtask.repository.JobRepository;
import com.arbeitnow.testtask.repository.entity.Job;
import com.arbeitnow.testtask.service.JobsRetrievalService;
import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class responsible for loading data to the DB and its update
 *
 * @author NikaKhrys
 */
@RequiredArgsConstructor
public class DBLoader {

  private static final long MILLISECONDS_TO_UPDATE = 604_800_000L;
  private static final int PAGES_TO_LOAD = 5;

  private final JobsRetrievalService jobsRetrievalService;
  private final JobMapper jobMapper;
  private final JobRepository jobRepository;

  /** Loads data from external api to the DB and updates it every week */
  @Scheduled(fixedDelay = MILLISECONDS_TO_UPDATE)
  @Transactional
  public void loadJobsData() {
    jobRepository.deleteAll();
    List<JobDto> jobDtos = jobsRetrievalService.getJobsFromApi(PAGES_TO_LOAD);
    List<Job> jobEntities = jobMapper.toEntities(jobDtos);
    jobRepository.saveAll(jobEntities);
  }
}
