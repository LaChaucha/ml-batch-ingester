package com.waterworks.mlqs.batch.ingester.infra.redisout.config;

import com.waterworks.mlqs.batch.ingester.app.core.MlBatchIngesterConstants;
import java.util.HashMap;
import java.util.Map;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisCommonConfig {

  private static final Long SALES_DB_TTL_CACHE_DEFAULT = 12 * 60 * 60 * 1000L;
  private static final Long STATUS_JOB_EXECUTION_TTL_CACHE_DEFAULT = 12 * 60 * 60 * 1000L;
  private static final Long SALES_DB_MAX_IDLE_TIME_CACHE_DEFAULT = 60 * 60 * 1000L;
  private static final Long STATUS_JOB_EXECUTION_MAX_IDLE_TIME_CACHE_DEFAULT = 60 * 60 * 1000L;


  /**
   * Creates a CacheManager bean for Redisson caching, configured with specific cache settings.
   *
   * @param redissonClient The Redisson client used for caching.
   * @return A CacheManager instance with predefined cache configurations.
   */
  @Bean
  public CacheManager cacheManager(@Autowired RedissonClient redissonClient) {
    Map<String, CacheConfig> config = new HashMap<>();
    config.put(MlBatchIngesterConstants.CACHE_NAMES.SALES_DB_CACHE.getValue(), new CacheConfig(
        SALES_DB_TTL_CACHE_DEFAULT,
        SALES_DB_MAX_IDLE_TIME_CACHE_DEFAULT));
    config.put(
        MlBatchIngesterConstants.CACHE_NAMES.STATUS_JOB_EXECUTION_CACHE.getValue(), new CacheConfig(
        STATUS_JOB_EXECUTION_TTL_CACHE_DEFAULT,
        STATUS_JOB_EXECUTION_MAX_IDLE_TIME_CACHE_DEFAULT));

    return new RedissonSpringCacheManager(redissonClient, config);
  }
}
