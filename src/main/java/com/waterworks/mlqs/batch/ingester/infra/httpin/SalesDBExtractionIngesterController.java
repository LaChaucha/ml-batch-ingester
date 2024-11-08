package com.waterworks.mlqs.batch.ingester.infra.httpin;

import com.waterworks.mlqs.batch.ingester.app.salesdb.SalesDBStartJobService;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs/salesdb")
@AllArgsConstructor
public class SalesDBExtractionIngesterController {

  private final SalesDBStartJobService salesDBStartJobService;

  @PostMapping("/{jobId}/start")
  public ResponseEntity<String> startJobs(@PathVariable final String jobId) {

    CompletableFuture.runAsync(() -> salesDBStartJobService.executeJob(jobId));
    // @TODO. change the response for an object.
    return ResponseEntity.ok("Running...");
  }

}
