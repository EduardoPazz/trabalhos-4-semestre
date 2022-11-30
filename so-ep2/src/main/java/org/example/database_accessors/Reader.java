package org.example.database_accessors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.example.helpers.SemaphoreHelper;

public class Reader extends DatabaseAccessor {

  public Reader(int index, List<String> database) {
    super(index, database);
  }

  @Override
  public void run() {
    System.out.println("Reader " + index + " started");

    ThreadLocalRandom.current().ints(MAX_RUNS, 0, database.size())
        .forEach(this::readDatabaseInIndex);

    try {
      sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Reader " + index + " finished");
  }

  private void readDatabaseInIndex(int i) {
    try {
      SemaphoreHelper.useRC(() -> {
        System.out.println(
            "Reader " + index + " is accessing database at index " + i);
        @SuppressWarnings("unused") String aLocalVariable = database.get(i);
        System.out.println(
            "Reader " + index + " finished accessing database at index " + i);
      });
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
