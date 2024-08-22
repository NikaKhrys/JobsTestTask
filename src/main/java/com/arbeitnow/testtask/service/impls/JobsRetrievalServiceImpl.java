package com.arbeitnow.testtask.service.impls;

import com.arbeitnow.testtask.service.JobsRetrievalService;
import com.arbeitnow.testtask.service.dto.JobDto;
import com.arbeitnow.testtask.service.exceptions.JobsParsingFailedException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@RequiredArgsConstructor
public class JobsRetrievalServiceImpl implements JobsRetrievalService {

  private static final String BASE_URL = "https://arbeitnow.com/api";
  private static final String URI = "/job-board-api";
  private final ObjectMapper objectMapper;

  public List<JobDto> getJobsFromApi(int pageNumber) {
    List<JobDto> jobs = new ArrayList<>();
    for (int i = 1; i <= pageNumber; i++) {
      jobs.addAll(getResponse(i));
    }
    return jobs;
  }

  private List<JobDto> getResponse(int page) {
    Mono<String> responseMono = sendRequest(page);
    return convertResponseToList(responseMono);
  }

  private List<JobDto> convertResponseToList(Mono<String> mono) {
    return mono.map(this::convertResponseStringToList).block();
  }

  private Mono<String> sendRequest(int page) {
    HttpClient httpClient = HttpClient.create().followRedirect(true);

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(BASE_URL)
        .build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(URI).queryParam("page", page).build())
        .retrieve()
        .bodyToFlux(DataBuffer.class)
        .map(
            dataBuffer -> {
              byte[] bytes = new byte[dataBuffer.readableByteCount()];
              dataBuffer.read(bytes);
              DataBufferUtils.release(dataBuffer);
              return new String(bytes);
            })
        .reduce((accumulated, current) -> accumulated + current);
  }

  private List<JobDto> convertResponseStringToList(String response) {
    try {
      JsonNode jsonNode = objectMapper.readTree(response);
      JsonNode dataArray = jsonNode.get("data");
      List<JobDto> jobDtos = new ArrayList<>();
      if (dataArray != null && dataArray.isArray()) {
        for (JsonNode dataObject : dataArray) {
          JobDto jobDto = objectMapper.treeToValue(dataObject, JobDto.class);
          jobDtos.add(jobDto);
        }
      }
      return jobDtos;
    } catch (Exception e) {
      throw new JobsParsingFailedException(e);
    }
  }
}
