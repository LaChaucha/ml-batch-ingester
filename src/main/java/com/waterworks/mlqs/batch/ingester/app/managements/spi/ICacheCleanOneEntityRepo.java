package com.waterworks.mlqs.batch.ingester.app.managements.spi;

import com.waterworks.mlqs.batch.ingester.app.core.MlBatchIngesterConstants;

/**
 * Interface defining the contract for cleaning caches associated with a specific entity.
 * Implementing classes are expected to provide a method ('clean') for clearing entity-specific
 * caches.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
public interface ICacheCleanOneEntityRepo {

  /**
   * Clears caches associated with a specific entity.
   * Implementing classes should provide the actual logic to retrieve the set of cache names
   * linked to the provided entity and clear the contents of each cache.
   *
   * @param entity Name of the entity for which caches need to be cleared.
   */
  void clean(final MlBatchIngesterConstants.CACHE_NAMES entity);
}
