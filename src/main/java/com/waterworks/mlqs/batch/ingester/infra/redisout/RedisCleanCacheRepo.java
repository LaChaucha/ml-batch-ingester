package com.waterworks.mlqs.batch.ingester.infra.redisout;

import com.waterworks.mlqs.batch.ingester.app.core.MlBatchIngesterConstants;
import com.waterworks.mlqs.batch.ingester.app.managements.spi.ICacheCleanAllRepo;
import com.waterworks.mlqs.batch.ingester.app.managements.spi.ICacheCleanOneEntityRepo;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

/**
 * Repository responsible for cleaning caches in Redis.
 * Implements both 'ICacheCleanOneEntityRepo' and 'ICacheCleanAllRepo' interfaces
 * to provide methods for clearing specific entity caches or all caches managed by the CacheManager.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class RedisCleanCacheRepo implements
    ICacheCleanOneEntityRepo,
    ICacheCleanAllRepo {
  private CacheManager cacheManager;

  /**
   * Clears all caches managed by the CacheManager.
   * Iterates through the cache names, retrieves each cache, and clears its contents.
   */
  @Override
  public void clean() {
    cacheManager.getCacheNames().stream()
        .map(cacheManager::getCache)
        .filter(Objects::nonNull)
        .forEach(Cache::clear);
  }

  /**
   * Clears caches associated with a specific entity.
   * Retrieves the set of cache names linked to the provided entity
   * and clears the contents of each cache.
   *
   * @param entity Name of the entity for which caches need to be cleared.
   */
  @Override
  public void clean(final MlBatchIngesterConstants.CACHE_NAMES entity) {
    Optional.ofNullable(entity)
        .ifPresent(cacheNames -> Objects.requireNonNull(
                cacheManager.getCache(cacheNames.getValue()))
            .clear());
  }
}
