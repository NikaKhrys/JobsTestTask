package com.arbeitnow.testtask.service.impls;

import com.arbeitnow.testtask.mappers.JobMapper;
import com.arbeitnow.testtask.repository.JobRepository;
import com.arbeitnow.testtask.repository.entity.Job;
import com.arbeitnow.testtask.service.JobService;
import com.arbeitnow.testtask.service.dto.JobDto;
import jakarta.persistence.Tuple;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
  private final JobMapper jobMapper;

  @Override
  public Page<JobDto> getAll(Pageable pageable) {
    Page<Job> jobs = jobRepository.findAll(pageable);
    return jobMapper.toDtos(jobs);
  }

  @Override
  public List<JobDto> getTopTen() {
    List<Job> jobs = jobRepository.findTopTen();
    return jobMapper.toDtos(jobs);
  }

  @Override
  public Map<String, Long> getStatisticsForLocations() {
    List<Tuple> statisticsList = jobRepository.countJobsByLocation();
    Map<String, Long> statistics = new LinkedHashMap<>();
    statisticsList.stream()
        .forEach(
            item ->
                statistics.put(
                    item.get("location", String.class), item.get("jobs_count", Long.class)));
    return statistics;
  }
}
