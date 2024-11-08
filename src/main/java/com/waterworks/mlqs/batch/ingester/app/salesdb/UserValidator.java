package com.waterworks.mlqs.batch.ingester.app.salesdb;

import com.waterworks.mlqs.batch.ingester.app.core.spi.ICacheValidationObjectRepo;
import com.waterworks.mlqs.batch.ingester.app.salesdb.domain.Sales_database_upsert_user;
import com.waterworks.mlqs.batch.ingester.app.salesdb.domain.UserRow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

/**
 * Validates and processes an AccountsRow object to generate an Account. This class implements the
 * ItemProcessor interface to validate AccountsRow data and convert it into an Account object if
 * validation passes.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
@AllArgsConstructor
@Slf4j
public class UserValidator implements ItemProcessor<UserRow, Sales_database_upsert_user> {

  private String jobId;
  private final ICacheValidationObjectRepo cacheValidationObjectRepo;

  @Override
  public Sales_database_upsert_user process(final UserRow userRow) {
    if (cacheValidationObjectRepo.checkIfWasNotSent(String.valueOf(userRow))) {
      return userRow.convertToUser();
    }
    return null;
  }
}
