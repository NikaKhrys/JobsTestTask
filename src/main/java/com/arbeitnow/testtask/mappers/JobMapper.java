package com.arbeitnow.testtask.mappers;

import com.arbeitnow.testtask.repository.entity.Job;
import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.data.domain.Page;

/**
 * Mapper abstract class for the Job entity
 *
 * @author NikaKhrys
 */
@Mapper(componentModel = ComponentModel.SPRING)
public abstract class JobMapper {

  public abstract Job toEntity(JobDto jobDto);

  public abstract JobDto toDto(Job job);

  public List<Job> toEntities(List<JobDto> jobDtos) {
    return jobDtos.stream().map(this::toEntity).toList();
  }

  public Page<JobDto> toDtos(Page<Job> jobs) {
    return jobs.map(this::toDto);
  }

  public List<JobDto> toDtos(List<Job> jobs) {
    return jobs.stream().map(this::toDto).toList();
  }
}
