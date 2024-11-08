package com.waterworks.mlqs.batch.ingester.app.images;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class ImagesReader implements ItemReader<File> {
  private final List<File> files;
  private int currentIndex = 0;

  public ImagesReader(final String filepath) {
    final File directory = new File(filepath);
    this.files =
        Arrays.asList(Objects.requireNonNull(
            directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"))));
  }

  @Override
  public File read() {
    if (currentIndex < files.size()) {
      return files.get(currentIndex++);
    } else {
      return null;
    }
  }
}
