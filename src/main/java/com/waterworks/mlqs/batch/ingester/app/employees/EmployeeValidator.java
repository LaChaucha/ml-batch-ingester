package com.waterworks.mlqs.batch.ingester.app.employees;

import com.waterworks.mlqs.batch.ingester.app.employees.domain.Hr_system_creation_employee;
import com.waterworks.mlqs.batch.ingester.app.employees.domain.EmployeeRow;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Slf4j
public class EmployeeValidator implements ItemProcessor<EmployeeRow, Hr_system_creation_employee> {

  private String jobId;

  @Override
  public Hr_system_creation_employee process(EmployeeRow employeeRow) {
    return employeeRow.convertToEmployee();
  }
}
