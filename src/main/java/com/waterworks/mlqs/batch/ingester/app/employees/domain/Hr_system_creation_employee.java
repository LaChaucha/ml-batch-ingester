package com.waterworks.mlqs.batch.ingester.app.employees.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hr_system_creation_employee {
  private String employeeId;
  private String name;
  private String position;
  private String email;
  private String phoneNumber;
  private Instant creationDate;
}
