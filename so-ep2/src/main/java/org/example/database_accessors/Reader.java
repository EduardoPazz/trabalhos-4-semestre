package org.example.database_accessors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.example.reader_writer_solution.ReaderWriter;

public class Reader extends DatabaseAccessor {

  private ReaderWriter controller;

  public Reader(int index, List<String> database) {
    super(index, database);
  }

  private void readerAndWriter(){
    controller.read(database, index);
    bdEsleep();
    controller.stopReading();
  }

  private void bdEsleep(){
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 100));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    System.out.println("Reader " + index + " started");

    readerAndWriter();

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
    @SuppressWarnings("unused") String aLocalVariable = database.get(i);
  }
}
