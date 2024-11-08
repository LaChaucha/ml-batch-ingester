package com.waterworks.mlqs.batch.ingester.infra.httpin;

import com.waterworks.mlqs.batch.ingester.app.employees.EmployeesStartJobService;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs/employees")
@AllArgsConstructor
public class EmployeesIngesterController {

  private final EmployeesStartJobService employeesStartJobService;

  @PostMapping("/{jobId}/start")
  public ResponseEntity<String> startJobs(@PathVariable final String jobId) {

    CompletableFuture.runAsync(() -> employeesStartJobService.executeJob(jobId));
    // @TODO. change the response for an object.
    return ResponseEntity.ok("Running...");
  }
}
