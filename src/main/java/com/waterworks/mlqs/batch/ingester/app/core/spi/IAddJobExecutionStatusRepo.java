package com.waterworks.mlqs.batch.ingester.app.core.spi;

import org.springframework.batch.core.JobExecution;

public interface IAddJobExecutionStatusRepo {
  void addStatus(final String jobId, final JobExecution object);
}
