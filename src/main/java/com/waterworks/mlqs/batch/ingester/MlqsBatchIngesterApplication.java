package com.waterworks.mlqs.batch.ingester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MlqsBatchIngesterApplication {

  /**
   * The main method is the entry point for the application. It initializes and starts the Spring
   * Boot application.
   *
   * @param args Command-line arguments that can be passed when launching the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(MlqsBatchIngesterApplication.class, args);
  }
}
