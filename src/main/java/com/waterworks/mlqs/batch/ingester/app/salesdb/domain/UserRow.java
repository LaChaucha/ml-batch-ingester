package com.waterworks.mlqs.batch.ingester.app.salesdb.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class UserRow {

  private String id;
  private String employeeJobTitle;
  private String employeeName;
  private String creationDate;
  private String modificationDate;
  private String m3Id;

  public Sales_database_upsert_user convertToUser() {
    return Sales_database_upsert_user.builder()
        .userId(this.id)
        .employeeJobTitle(this.employeeJobTitle)
        .employeeName(this.employeeName)
        .creationDate(this.creationDate)
        .modificationDate(this.modificationDate)
        .m3Id(this.m3Id)
        .build();
  }

  public static UserRow mapRow(ResultSet rs, int rowNum) throws SQLException {
    return UserRow.builder()
        .id(rs.getString("id"))
        .employeeJobTitle(rs.getString("employee_job_title"))
        .employeeName(rs.getString("employee_name"))
        .creationDate(String.valueOf(rs.getTimestamp("creation_date")))
        .modificationDate(String.valueOf(rs.getTimestamp("modification_date")))
        .m3Id(String.valueOf(rs.getInt("m3_id")))
        .build();
  }


}
