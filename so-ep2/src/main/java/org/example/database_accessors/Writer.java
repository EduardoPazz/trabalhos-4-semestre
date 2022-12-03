package org.example.database_accessors;

import java.util.concurrent.ThreadLocalRandom;
import org.example.db.DB;

public class Writer extends DatabaseAccessor {

  private static final String MODIFIED_FLAG = "MODIFICADO";

  public Writer(int i, DB database) {
    super(i, database);
  }

  @Override
  public void run() {
    ThreadLocalRandom.current().ints(MAX_RUNS, 0, database.size())
        .forEach(i -> database.set(i, MODIFIED_FLAG));

    try {
      sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
