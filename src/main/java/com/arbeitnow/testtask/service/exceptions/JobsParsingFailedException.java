package com.arbeitnow.testtask.service.exceptions;

public class JobsParsingFailedException extends RuntimeException {

  private static final String MESSAGE = "Failed to parse jobs information from api";

  public JobsParsingFailedException(Exception e) {
    super(MESSAGE, e);
  }
}
