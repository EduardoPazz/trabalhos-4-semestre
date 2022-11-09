package org.example.database_clients;

import java.util.concurrent.ThreadLocalRandom;

public class Writer extends DatabaseClient {

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
