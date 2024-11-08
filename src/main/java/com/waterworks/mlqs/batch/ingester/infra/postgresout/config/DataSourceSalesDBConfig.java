package com.waterworks.mlqs.batch.ingester.infra.postgresout.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class DataSourceSalesDBConfig {

  @Value("${salesdb.datasource.driver}")
  private String salesdbDataSourceDriver;

  @Value("${salesdb.datasource.url}")
  private String salesdbDataSourceUrl;

  @Value("${salesdb.datasource.user}")
  private String salesdbDataSourceUser;

  @Value("${salesdb.datasource.password}")
  private String salesdbDataSourcePassword;

  @Bean(name = "salesdbDataSource")
  public DataSource salesdbDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(salesdbDataSourceDriver);
    dataSource.setUrl(salesdbDataSourceUrl);
    dataSource.setUsername(salesdbDataSourceUser);
    dataSource.setPassword(salesdbDataSourcePassword);
    return dataSource;
  }
  @Bean
  public DataSourceInitializer salesdbDataSourceInitializer(@Qualifier("salesdbDataSource") DataSource dataSource) {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    return initializer;
  }

  @Bean(name = "salesdbJdbcTemplate")
  public JdbcTemplate salesdbJdbcTemplate(@Qualifier("salesdbDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}