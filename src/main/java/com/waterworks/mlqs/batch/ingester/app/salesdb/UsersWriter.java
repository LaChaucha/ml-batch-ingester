package com.waterworks.mlqs.batch.ingester.app.salesdb;

import com.waterworks.mlqs.batch.ingester.app.salesdb.domain.Sales_database_upsert_user;
import org.springframework.batch.item.data.MongoItemWriter;

/**
 * Writes Account data to the database using JDBC. This class implements the ItemWriter interface
 * for writing chunks of Users data to the database.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
public class UsersWriter extends MongoItemWriter<Sales_database_upsert_user> {

}