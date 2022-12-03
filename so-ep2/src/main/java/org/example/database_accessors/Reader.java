package org.example.database_accessors;

import java.util.concurrent.ThreadLocalRandom;
import org.example.db.DB;

public class Reader extends DatabaseAccessor {

  public Reader(int index, DB database) {
    super(index, database);
  }

  @Override
  public void run() {
    ThreadLocalRandom.current().ints(MAX_RUNS, 0, database.size())
        .forEach(i -> {
          @SuppressWarnings("unused") String aLocalVariable = database.get(i);
        });

    try {
      sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
