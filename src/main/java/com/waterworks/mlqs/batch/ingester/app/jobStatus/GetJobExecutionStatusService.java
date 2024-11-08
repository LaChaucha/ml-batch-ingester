package com.waterworks.mlqs.batch.ingester.app.jobStatus;

import com.waterworks.mlqs.batch.ingester.app.jobStatus.domain.JobStatus;
import com.waterworks.mlqs.batch.ingester.app.jobStatus.spi.IGetJobExecutionStatusRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetJobExecutionStatusService {

  private final IGetJobExecutionStatusRepo getJobExecutionStatusRepo;

  public JobStatus getStatusByJobId(final String jobId) {
    return getJobExecutionStatusRepo.getStatus(jobId);
  }
}
