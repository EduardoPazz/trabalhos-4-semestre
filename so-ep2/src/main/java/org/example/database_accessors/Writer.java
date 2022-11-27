package org.example.database_accessors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.example.reader_writer_solution.ReaderWriter;

public class Writer extends DatabaseAccessor {

  private static final String MODIFIED_FLAG = "MODIFICADO";
  private ReaderWriter controller;

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
    database.set(i, MODIFIED_FLAG);
  }
}
