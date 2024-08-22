package com.arbeitnow.testtask.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationJobCountDto {
  private String location;
  private int jobsCount;
}
