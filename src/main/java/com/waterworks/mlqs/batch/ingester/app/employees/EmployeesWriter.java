package com.waterworks.mlqs.batch.ingester.app.employees;

import com.waterworks.mlqs.batch.ingester.app.employees.domain.Hr_system_creation_employee;
import org.springframework.batch.item.data.MongoItemWriter;

/**
 * Writes Account data to the database using JDBC. This class implements the ItemWriter interface
 * for writing chunks of Account data to the database.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
public class EmployeesWriter extends MongoItemWriter<Hr_system_creation_employee> {

}