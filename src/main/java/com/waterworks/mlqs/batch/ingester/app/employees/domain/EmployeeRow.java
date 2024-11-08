package com.waterworks.mlqs.batch.ingester.app.employees.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Slf4j
public class EmployeeRow {

  private String employeeId;
  private String name;
  private String position;
  private String email;
  private String phoneNumber;

  public Hr_system_creation_employee convertToEmployee() {
    return Hr_system_creation_employee.builder()
        .employeeId(this.employeeId)
        .name(this.name)
        .position(this.position)
        .email(this.email)
        .phoneNumber(this.phoneNumber)
        .creationDate(Instant.now())
        .build();
  }

  public static EmployeeRow getDTOFromEmployee(
      final Hr_system_creation_employee HRSystemCreationEmployee) {
    return EmployeeRow.builder()
        .employeeId(HRSystemCreationEmployee.getEmployeeId())
        .name(HRSystemCreationEmployee.getName())
        .position(HRSystemCreationEmployee.getPosition())
        .email(HRSystemCreationEmployee.getEmail())
        .phoneNumber(HRSystemCreationEmployee.getPhoneNumber())
        .build();
  }

}
