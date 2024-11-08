package com.waterworks.mlqs.batch.ingester.app.core.spi;

public interface ICacheValidationObjectRepo {
  boolean checkIfWasNotSent(final String object);
}
