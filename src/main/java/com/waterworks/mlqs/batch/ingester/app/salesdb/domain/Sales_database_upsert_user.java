package com.waterworks.mlqs.batch.ingester.app.salesdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sales_database_upsert_user {
  private String userId;
  private String employeeJobTitle;
  private String employeeName;
  private String creationDate;
  private String modificationDate;
  private String m3Id;
}
