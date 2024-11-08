package com.waterworks.mlqs.batch.ingester.infra.bucketout.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

  @Value("${buckets.images.url}")
  private String url;

  @Value("${buckets.images.access}")
  private String access;

  @Value("${buckets.images.secret}")
  private String secret;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
        .endpoint(url)
        .credentials(access, secret)
        .build();
  }

}
