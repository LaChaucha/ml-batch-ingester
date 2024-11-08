package com.waterworks.mlqs.batch.ingester.app.salesdb.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<UserRow> {
  public UserRow mapRow(ResultSet rs, int rowNum) throws SQLException {
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
