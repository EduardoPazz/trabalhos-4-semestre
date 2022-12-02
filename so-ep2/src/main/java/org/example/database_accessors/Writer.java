package org.example.database_accessors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.example.helpers.SemaphoreHelper;

public class Writer extends DatabaseAccessor {

  private static final String MODIFIED_FLAG = "MODIFICADO";

  public Writer(int i, List<String> database) {
    super(i, database);
  }

  @Override
  public void run() {
    ThreadLocalRandom.current().ints(MAX_RUNS, 0, database.size())
        .forEach(this::writeToDatabaseInIndex);

    try {
      sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void writeToDatabaseInIndex(int i) {
    try {
      SemaphoreHelper.useDB(() -> {
        database.set(i, MODIFIED_FLAG);
      });
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
