package org.example.database_accessors;

import java.util.concurrent.ThreadLocalRandom;

public class Writer extends DatabaseAccessor {

  private static final String MODIFIED_FLAG = "MODIFICADO";

  @Override
  public void run() {
    ThreadLocalRandom.current().ints(MAX_RUNS, 0, database.size())
        .forEach(i -> {
          database.set(i, MODIFIED_FLAG);
        });
    try {
      sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
