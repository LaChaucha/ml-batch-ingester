package com.waterworks.mlqs.batch.ingester.app.core;

import lombok.Getter;

public class MlBatchIngesterConstants {
  @Getter
  public enum CACHE_NAMES {
    SALES_DB_CACHE("sales_db_cache"),
    STATUS_JOB_EXECUTION_CACHE("status_job_execution_cache"),
    UNKNOWN("unknown");
    private final String value;

    CACHE_NAMES(final String value) {
      this.value = value;
    }

    public static CACHE_NAMES getCacheNamesFromString(String type) {
      return switch (String.valueOf(type).toUpperCase()) {
        case "SALES_DB_CACHE" -> SALES_DB_CACHE;
        case "STATUS_JOB_EXECUTION_CACHE" -> STATUS_JOB_EXECUTION_CACHE;
        default -> UNKNOWN;
      };
    }
  }

}
