package com.waterworks.mlqs.batch.ingester.app.jobStatus.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobStatus {
  private String id;
  private String status;
}
