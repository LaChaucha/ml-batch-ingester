package com.waterworks.mlqs.batch.ingester.infra.bucketout;

import com.waterworks.mlqs.batch.ingester.app.images.spi.IBucketRepo;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MinioRepo implements IBucketRepo {

  private final MinioClient minioClient;
  private final String SYSTEM_ORIGIN_AND_EVENTS = "product-catalog-creation-product-images";

  @Override
  public void saveFile(File file) {
    try {
      if (!minioClient.bucketExists(
          BucketExistsArgs.builder().bucket(SYSTEM_ORIGIN_AND_EVENTS).build())) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(SYSTEM_ORIGIN_AND_EVENTS).build());
      }
      minioClient.uploadObject(
          UploadObjectArgs.builder()
              .bucket(SYSTEM_ORIGIN_AND_EVENTS)
              .object(file.getName())
              .filename(file.getAbsolutePath())
              .build());

    } catch (ErrorResponseException | InsufficientDataException | InternalException |
             InvalidKeyException | InvalidResponseException | IOException |
             NoSuchAlgorithmException | ServerException | XmlParserException e) {
      throw new RuntimeException(e);
    }
  }
}
