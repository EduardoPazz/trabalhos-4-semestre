package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.example.database_accessors.DatabaseAccessor;
import org.example.db.BlockingDB;
import org.example.db.ReadPreferenceDB;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;

class Main {

  public static void main(String[] args) throws IOException {
    List<String> database = IOHelper.readDatabase();

    int MAX_PROPORTIONS = 100;
    int MAX_LOOPS = 50;

    long[][] blockingModeResults = new long[MAX_PROPORTIONS + 1][MAX_LOOPS];
    long[][] readerPreferenceModeResults = new long[MAX_PROPORTIONS
        + 1][MAX_LOOPS];

    // ***************** Blocking Mode *****************
    for (int i = 0; i <= MAX_PROPORTIONS; i++) {
      for (int j = 0; j < MAX_LOOPS; j++) {
        BlockingDB localDatabase = new BlockingDB(database);
        List<DatabaseAccessor> databaseAccessorsBlockingMode = RandomnessHelper.getShuffledDatabaseAccessors(
            localDatabase, i);

        long startTime = System.currentTimeMillis();

        databaseAccessorsBlockingMode.forEach(DatabaseAccessor::start);
        joinAllThreads(databaseAccessorsBlockingMode);

        long delta = System.currentTimeMillis() - startTime;

        blockingModeResults[i][j] = delta;
      }
    }

    // ***************** Reader Preference Mode *****************
    for (int i = 0; i <= MAX_PROPORTIONS; i++) {
      for (int j = 0; j < MAX_LOOPS; j++) {
        ReadPreferenceDB localDatabase = new ReadPreferenceDB(database);
        List<DatabaseAccessor> databaseAccessorsReaderPreferenceMode = RandomnessHelper.getShuffledDatabaseAccessors(
            localDatabase, i);

        long startTime = System.currentTimeMillis();

        databaseAccessorsReaderPreferenceMode.forEach(DatabaseAccessor::start);
        joinAllThreads(databaseAccessorsReaderPreferenceMode);

        long delta = System.currentTimeMillis() - startTime;

        readerPreferenceModeResults[i][j] = delta;
      }
    }

    // Log out results
    System.out.println("Blocking mode: \n\nreader_proportion,average_time");

    for (int i = 0; i <= MAX_PROPORTIONS; i++) {
      System.out.println(
          i + "," + Arrays.stream(blockingModeResults[i]).average()
              .getAsDouble());
    }

    System.out.println(
        "\n\nReader Preference mode: \n\nreader_proportion,average_time");
    for (int i = 0; i <= MAX_PROPORTIONS; i++) {
      System.out.println(
          i + "," + Arrays.stream(readerPreferenceModeResults[i]).average()
              .getAsDouble());
    }
  }

  private static void joinAllThreads(List<DatabaseAccessor> databaseAccessors) {
    databaseAccessors.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
