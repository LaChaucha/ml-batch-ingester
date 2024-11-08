package com.waterworks.mlqs.batch.ingester.app.employees.config;

import com.waterworks.mlqs.batch.ingester.app.employees.EmployeeReader;
import com.waterworks.mlqs.batch.ingester.app.employees.EmployeeValidator;
import com.waterworks.mlqs.batch.ingester.app.employees.EmployeesWriter;
import com.waterworks.mlqs.batch.ingester.app.employees.domain.Hr_system_creation_employee;
import com.waterworks.mlqs.batch.ingester.app.employees.domain.EmployeeRow;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@Slf4j
public class EmployeesBatchConfiguration {
  private static final int CHUNK_SIZE = 5000;

  @Bean
  public Job employeesJob(JobRepository jobRepository,
                          Step processEmployees) {
    return new JobBuilder("employeesJob", jobRepository)
        .start(processEmployees)
        //.next(fileRemovalStep)
        .build();
  }

  @Bean
  public Step processEmployees(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               ItemReader<EmployeeRow> employeesReader,
                               ItemProcessor<EmployeeRow, Hr_system_creation_employee> employeesValidationProcessor,
                               MongoItemWriter<Hr_system_creation_employee> employeesWriter) {
    return new StepBuilder("processEmployees", jobRepository)
        .<EmployeeRow, Hr_system_creation_employee>chunk(CHUNK_SIZE, transactionManager)
        .reader(employeesReader)
        .processor(employeesValidationProcessor)
        .writer(employeesWriter)
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<EmployeeRow> employeesReader(
      @Value("#{jobParameters['jobId']}") final String jobId,
      @Value("${files.employees.path}") final String filepath) {
    return new EmployeeReader(jobId, filepath);
  }

  @Bean
  @StepScope
  public ItemProcessor<EmployeeRow, Hr_system_creation_employee> employeesValidationProcessor(
      @Value("#{jobParameters['jobId']}") String jobId) {
    return new EmployeeValidator(jobId);
  }

  @Bean
  @StepScope
  public MongoItemWriter<Hr_system_creation_employee> employeesWriter(MongoTemplate mongoTemplate) {
    EmployeesWriter employeesWriter = new EmployeesWriter();
    employeesWriter.setTemplate(mongoTemplate);
    return employeesWriter;
  }
}
