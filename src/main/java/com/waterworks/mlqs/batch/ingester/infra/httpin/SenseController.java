package com.waterworks.mlqs.batch.ingester.infra.httpin;

import com.waterworks.mlqs.batch.ingester.app.jobStatus.GetJobExecutionStatusService;
import com.waterworks.mlqs.batch.ingester.app.jobStatus.domain.JobStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SenseController {
  private final GetJobExecutionStatusService getJobExecutionStatusService;
  @GetMapping("/jobs/{jobId}/sense")
  public ResponseEntity<JobStatus> senseJobs(@PathVariable final String jobId){
    return ResponseEntity.ok(getJobExecutionStatusService.getStatusByJobId(jobId));
  }
}
