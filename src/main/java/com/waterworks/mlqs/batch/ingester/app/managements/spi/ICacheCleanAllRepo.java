package com.waterworks.mlqs.batch.ingester.app.managements.spi;

/**
 * Interface defining the contract for cleaning all caches managed by the CacheManager.
 * Implementing classes are expected to provide a method ('clean') for clearing all caches.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
public interface ICacheCleanAllRepo {

  /**
   * Clears all caches managed by the CacheManager.
   * Implementing classes should provide the actual logic to iterate through cache names,
   * retrieve each cache, and clear its contents.
   */
  void clean();
}
