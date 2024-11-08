package com.waterworks.mlqs.batch.ingester.app.images.spi;

import java.io.File;

public interface IBucketRepo {
  void saveFile(final File file);
}
