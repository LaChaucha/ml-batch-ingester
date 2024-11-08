package com.waterworks.mlqs.batch.ingester.app.core;


import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

  @Bean
  public Map<String, JobExecution> jobExecutionMap(){
    return new HashMap<String, JobExecution>();
  }

}
