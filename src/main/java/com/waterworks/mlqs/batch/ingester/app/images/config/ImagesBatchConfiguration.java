package com.waterworks.mlqs.batch.ingester.app.images.config;

import com.waterworks.mlqs.batch.ingester.app.images.ImagesReader;
import com.waterworks.mlqs.batch.ingester.app.images.ImagesWriter;
import com.waterworks.mlqs.batch.ingester.app.images.spi.IBucketRepo;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class ImagesBatchConfiguration {
  private static final int CHUNK_SIZE = 5000;

  @Bean
  public Job imagesJob(JobRepository jobRepository,
                       Step processImages) {
    return new JobBuilder("imagesJob", jobRepository)
        .start(processImages)
        .build();
  }

  @Bean
  public Step processImages(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            ItemReader<File> imagesReader,
                            ItemWriter<Object> imagesWriter) {
    return new StepBuilder("processImages", jobRepository)
        .chunk(CHUNK_SIZE, transactionManager)
        .reader(imagesReader)
        .writer(imagesWriter)
        .build();
  }

  @Bean
  @StepScope
  public ItemReader<File> imagesReader(@Value("${files.images.path}") final String filepath) {
    return new ImagesReader(filepath);
  }

  @Bean
  @StepScope
  public ItemWriter<Object> imagesWriter(
      final IBucketRepo bucketRepo
  ) {
    return new ImagesWriter(bucketRepo);
  }

}
