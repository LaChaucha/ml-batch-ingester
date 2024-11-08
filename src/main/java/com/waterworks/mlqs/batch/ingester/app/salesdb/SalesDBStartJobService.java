package com.waterworks.mlqs.batch.ingester.app.salesdb;

import com.waterworks.mlqs.batch.ingester.app.core.spi.IAddJobExecutionStatusRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SalesDBStartJobService {
  private final JobLauncher jobLauncher;
  private final Job salesDbJob;
  private final IAddJobExecutionStatusRepo addJobExecutionStatusRepo;

  @Autowired
  public SalesDBStartJobService(final JobLauncher jobLauncher,
                                @Qualifier("salesDbJob") final Job salesDbJob,
                                final IAddJobExecutionStatusRepo addJobExecutionStatusRepo
  ) {
    this.jobLauncher = jobLauncher;
    this.salesDbJob = salesDbJob;
    this.addJobExecutionStatusRepo = addJobExecutionStatusRepo;
  }

  public void executeJob(final String jobId) {

    JobParameters jobParameters = new JobParametersBuilder()
        .addString("jobId", jobId)
        .toJobParameters();

    try {
      addJobExecutionStatusRepo.addStatus(jobId, jobLauncher.run(salesDbJob, jobParameters));

    } catch (JobExecutionAlreadyRunningException | JobRestartException |
             JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
      throw new RuntimeException(e);
    }
  }
}
