package com.waterworks.mlqs.batch.ingester.infra.redisout;

import com.waterworks.mlqs.batch.ingester.app.core.MlBatchIngesterConstants;
import com.waterworks.mlqs.batch.ingester.app.core.spi.ICacheValidationObjectRepo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CacheValidationObjectRepo
    implements ICacheValidationObjectRepo {
  final RedissonClient redissonClient;
  final Map<String,String> cache;

  @Autowired
  public CacheValidationObjectRepo(final RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
    this.cache = redissonClient.getMap(MlBatchIngesterConstants.CACHE_NAMES.SALES_DB_CACHE.getValue());
  }

  @Override
  public boolean checkIfWasNotSent(final String object) {

    final String hashedObject = generateSHA256(object);
    if(Objects.nonNull(this.cache.get(hashedObject))) {
      return false;
    }
    this.cache.put(hashedObject,object);
    return true;
  }

  public static String generateSHA256(String input) {
    try {
      // Get instance of MessageDigest for SHA-256
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      // Apply the hash to the input bytes
      byte[] hash = digest.digest(input.getBytes());

      // Convert the byte array to hexadecimal format
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      // Handle an exception if the algorithm is not available
      e.printStackTrace();
      return null;
    }
  }
}
