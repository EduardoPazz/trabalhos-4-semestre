package org.example.database_accessors;

import java.util.concurrent.ThreadLocalRandom;

public class Reader extends DatabaseAccessor {

  @Override
  public void run() {
    ThreadLocalRandom.current().ints(MAX_RUNS, 0, database.size())
        .forEach(i -> {
          String aLocalVariable = database.get(i);
        });
    try {
      sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
