package com.waterworks.mlqs.batch.ingester.app.employees;

import org.springframework.core.convert.converter.Converter;

public class EmptyStringToFalseConverter implements Converter<String, Boolean> {

  @Override
  public Boolean convert(String source) {
    return source.isEmpty() ? Boolean.FALSE : Boolean.valueOf(source);
  }
}