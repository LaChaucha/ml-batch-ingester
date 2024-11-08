package com.waterworks.mlqs.batch.ingester.app.managements;

import com.waterworks.mlqs.batch.ingester.app.core.MlBatchIngesterConstants;
import com.waterworks.mlqs.batch.ingester.app.managements.spi.ICacheCleanAllRepo;
import com.waterworks.mlqs.batch.ingester.app.managements.spi.ICacheCleanOneEntityRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

 /**
 * Service class responsible for providing high-level cache cleaning operations.
 * Uses 'ICacheCleanOneEntityRepo' and 'ICacheCleanAllRepo' repositories to clean specific
 * entity caches or all caches managed by the CacheManager.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CacheCleanService {

  private final ICacheCleanOneEntityRepo cacheCleanOneEntityService;
  private final ICacheCleanAllRepo cacheCleanAllService;

   /**
    * Cleans all caches managed by the CacheManager.
    * Delegates the operation to the 'clean' method in 'ICacheCleanAllRepo'.
    */
  public void cleanCache() {
    cacheCleanAllService.clean();
  }

   /**
    * Cleans caches associated with a specific entity.
    * Delegates the operation to the 'clean' method in 'ICacheCleanOneEntityRepo'.
    *
    * @param entity Name of the entity for which caches need to be cleared.
    */
  public void cleanCache(final MlBatchIngesterConstants.CACHE_NAMES entity) {
    cacheCleanOneEntityService.clean(entity);
  }
}
