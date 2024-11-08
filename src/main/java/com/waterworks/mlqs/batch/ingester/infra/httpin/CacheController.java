package com.waterworks.mlqs.batch.ingester.infra.httpin;

import com.waterworks.mlqs.batch.ingester.app.core.MlBatchIngesterConstants;
import com.waterworks.mlqs.batch.ingester.app.managements.CacheCleanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for managing cache-related HTTP requests.
 * Provides endpoints to clean specific entity caches or all caches managed by the CacheManager.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CacheController {

  private final CacheCleanService cacheCleanService;

  /**
   * Endpoint to clean all caches managed by the CacheManager.
   * Invokes the 'cleanCache' method in 'CachesRefreshService' to clear all caches.
   *
   * @return ResponseEntity with a success message indicating all caches are cleaned.
   */
  @DeleteMapping("/caches")
  public ResponseEntity<String> cleanCache() {
    cacheCleanService.cleanCache();
    return ResponseEntity.ok("All caches cleaned.");
  }

  /**
   * Endpoint to clean the 'salesdb' cache.
   * Invokes the 'cleanCache' method in 'CachesRefreshService' for 'salesdb' entity.
   *
   * @return ResponseEntity with a success message indicating the 'salesdb' cache is cleaned.
   */
  @DeleteMapping("/jobs/salesdb/cache")
  public ResponseEntity<String> cleanSalesDbCache() {
    cacheCleanService.cleanCache(MlBatchIngesterConstants.CACHE_NAMES.SALES_DB_CACHE);
    return ResponseEntity.ok("Sales DB caches cleaned.");
  }

  @DeleteMapping("/jobs/states/cache")
  public ResponseEntity<String> cleanStatesJobsCache() {
    cacheCleanService.cleanCache(MlBatchIngesterConstants.CACHE_NAMES.STATUS_JOB_EXECUTION_CACHE);
    return ResponseEntity.ok("States job caches cleaned.");
  }
}
