package com.waterworks.mlqs.batch.ingester.app.employees;

import com.waterworks.mlqs.batch.ingester.app.employees.domain.EmployeeRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
public class EmployeeReader extends FlatFileItemReader<EmployeeRow> {
  public EmployeeReader(final String jobId, final String filepath) {
    Resource resource = new FileSystemResource(filepath);
    this.setResource(resource);
    DefaultLineMapper<EmployeeRow> defaultMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer myTokenizer = new DelimitedLineTokenizer();
    myTokenizer.setNames(
        "employeeId",
        "name",
        "position",
        "email",
        "phoneNumber"
    );
    defaultMapper.setLineTokenizer(myTokenizer);
    BeanWrapperFieldSetMapper<EmployeeRow> beanWrapper = new BeanWrapperFieldSetMapper<>();
    DefaultConversionService conversionService = new DefaultConversionService();
    conversionService.addConverter(new EmptyStringToFalseConverter());
    beanWrapper.setConversionService(conversionService);
    beanWrapper.setTargetType(EmployeeRow.class);
    defaultMapper.setFieldSetMapper(beanWrapper);
    this.setLineMapper(defaultMapper);
  }
}
