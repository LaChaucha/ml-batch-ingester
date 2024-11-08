package com.waterworks.mlqs.batch.ingester.infra.redisout;

import com.waterworks.mlqs.batch.ingester.app.core.MlBatchIngesterConstants;
import com.waterworks.mlqs.batch.ingester.app.core.spi.IAddJobExecutionStatusRepo;
import com.waterworks.mlqs.batch.ingester.app.jobStatus.domain.JobStatus;
import com.waterworks.mlqs.batch.ingester.app.jobStatus.spi.IGetJobExecutionStatusRepo;
import java.util.Map;
import java.util.Objects;
import org.redisson.api.RedissonClient;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Repository;

@Repository
public class CacheJobExecutionRepo implements
    IGetJobExecutionStatusRepo,
    IAddJobExecutionStatusRepo {
  final RedissonClient redissonClient;
  Map<String, JobExecution> statusMap;

  public CacheJobExecutionRepo(final RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
    this.statusMap = redissonClient.getMap(
        MlBatchIngesterConstants.CACHE_NAMES.STATUS_JOB_EXECUTION_CACHE.getValue());
  }

  @Override
  public JobStatus getStatus(String jobId) {
    final JobExecution jobExecution = statusMap.get(jobId);
    return JobStatus.builder()
        .id(jobId)
        .status(Objects.isNull(jobExecution)
            ? "invalid"
            : String.valueOf(jobExecution.getStatus()).toLowerCase())
        .build();
  }

  @Override
  public void addStatus(final String jobId, final JobExecution object) {
    statusMap.put(jobId, object);
  }
}
