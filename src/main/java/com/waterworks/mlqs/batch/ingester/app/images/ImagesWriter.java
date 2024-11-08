package com.waterworks.mlqs.batch.ingester.app.images;

import com.waterworks.mlqs.batch.ingester.app.images.spi.IBucketRepo;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * Writes Account data to the database using JDBC. This class implements the ItemWriter interface
 * for writing chunks of Account data to the database.
 *
 * @author Edgar Thomson
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
public class ImagesWriter implements ItemWriter<Object> {

  private final IBucketRepo bucketRepo;

  @Override
  public void write(Chunk<?> chunk) throws Exception {
    chunk.forEach( file -> bucketRepo.saveFile((File) file));
  }
}