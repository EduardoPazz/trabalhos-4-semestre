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
    System.out.println("Writer " + index + " started");

    ThreadLocalRandom.current().ints(MAX_RUNS, 0, database.size())
        .forEach(this::writeToDatabaseInIndex);

    try {
      sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Writer " + index + " finished");
  }

  private void writeToDatabaseInIndex(int i) {
    try {
      SemaphoreHelper.useDB(() -> {
        System.out.println(
            "Writer " + index + " is accessing database at index " + i);
        database.set(i, MODIFIED_FLAG);
        System.out.println(
            "Writer " + index + " finished accessing database at index " + i);
      });
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
