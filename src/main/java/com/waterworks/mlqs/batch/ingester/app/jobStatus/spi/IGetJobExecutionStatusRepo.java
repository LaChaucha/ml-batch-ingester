package com.waterworks.mlqs.batch.ingester.app.jobStatus.spi;

import com.waterworks.mlqs.batch.ingester.app.jobStatus.domain.JobStatus;

public interface IGetJobExecutionStatusRepo {
  JobStatus getStatus(final String jobId);
}
