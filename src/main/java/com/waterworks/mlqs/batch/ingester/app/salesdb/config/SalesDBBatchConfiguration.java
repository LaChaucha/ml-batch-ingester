package com.waterworks.mlqs.batch.ingester.app.salesdb.config;

import com.mongodb.client.MongoClient;
import com.waterworks.mlqs.batch.ingester.app.core.spi.ICacheValidationObjectRepo;
import com.waterworks.mlqs.batch.ingester.app.salesdb.UserValidator;
import com.waterworks.mlqs.batch.ingester.app.salesdb.UsersWriter;
import com.waterworks.mlqs.batch.ingester.app.salesdb.domain.Sales_database_upsert_user;
import com.waterworks.mlqs.batch.ingester.app.salesdb.domain.UserRow;
import com.waterworks.mlqs.batch.ingester.app.salesdb.domain.UserRowMapper;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@Slf4j
public class SalesDBBatchConfiguration {
  private static final int CHUNK_SIZE = 5000;
  private static final String DATABASE_NAME = "sales";
  private static final String USER_TABLE_QUERY = """
      SELECT id, employee_job_title, employee_name, creation_date, modification_date, m3_id
      FROM public.users
      """;

  @Bean
  public Job salesDbJob(JobRepository jobRepository,
                          Step processSalesUserTable) {
    return new JobBuilder("salesDbJob", jobRepository)
        .start(processSalesUserTable)
        .build();
  }

  @Bean
  public Step processSalesUserTable(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               JdbcCursorItemReader<UserRow> userReader,
                               ItemProcessor<UserRow, Sales_database_upsert_user>
                                          usersValidationProcessor,
                               MongoItemWriter<Sales_database_upsert_user> usersWriter) {
    return new StepBuilder("processSalesUserTable", jobRepository)
        .<UserRow, Sales_database_upsert_user>chunk(CHUNK_SIZE, transactionManager)
        .reader(userReader)
        .processor(usersValidationProcessor)
        .writer(usersWriter)
        .build();
  }

  @Bean
  @StepScope
  public JdbcCursorItemReader<UserRow> userReader(
      @Value("#{jobParameters['jobId']}") final String jobId,
      @Qualifier("salesdbDataSource") final DataSource dataSource) {
    return new JdbcCursorItemReaderBuilder<UserRow>()
        .dataSource(dataSource)
        .name("userReader")
        .sql(USER_TABLE_QUERY)
        .rowMapper(new UserRowMapper())
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<UserRow, Sales_database_upsert_user> userValidationProcessor(
      @Value("#{jobParameters['jobId']}") final String jobId,
      final ICacheValidationObjectRepo cacheValidationObjectRepo) {
    return new UserValidator(jobId, cacheValidationObjectRepo);
  }

  @Bean
  @StepScope
  public MongoItemWriter<Sales_database_upsert_user> usersWriter(final MongoClient mongoClient) {
    final UsersWriter usersWriter = new UsersWriter();
    usersWriter.setTemplate(new MongoTemplate(mongoClient, DATABASE_NAME));
    return usersWriter;
  }
}
