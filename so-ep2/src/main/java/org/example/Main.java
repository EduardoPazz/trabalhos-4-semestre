package org.example;

import static org.example.helpers.SemaphoreHelper.Mode.BLOCKING;
import static org.example.helpers.SemaphoreHelper.Mode.READER_PREFERENCE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.example.database_accessors.DatabaseAccessor;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;
import org.example.helpers.SemaphoreHelper;

class Main {

  public static void main(String[] args) throws IOException {
    List<String> database = IOHelper.readDatabase();

    int MAX_PROPORTIONS = 100;
    int MAX_LOOPS = 50;

    long[][] blockingModeResults = new long[MAX_PROPORTIONS+1][MAX_LOOPS];
    long[][] readerPreferenceModeResults = new long[MAX_PROPORTIONS+1][MAX_LOOPS];

    // Run test cases in all proportions
    for (int i = 0; i <= MAX_PROPORTIONS; i++) {
      for (int j = 0; j < MAX_LOOPS; j++) {
        // ***************** Blocking Mode *****************

        List<DatabaseAccessor> databaseAccessorsBlockingMode = RandomnessHelper.getShuffledDatabaseAccessors(
            database, i);

        long startTime = System.currentTimeMillis();

        runBlockingMode(databaseAccessorsBlockingMode);
        joinAllThreads(databaseAccessorsBlockingMode);

        long delta = System.currentTimeMillis() - startTime;

        blockingModeResults[i][j] = delta;

        // ***************** Reader Preference Mode *****************

        List<DatabaseAccessor> databaseAccessorsReaderPreferenceMode = RandomnessHelper.getShuffledDatabaseAccessors(
            database, i);

        startTime = System.currentTimeMillis();

        runReaderPreferenceMode(databaseAccessorsReaderPreferenceMode);
        joinAllThreads(databaseAccessorsReaderPreferenceMode);

        delta = System.currentTimeMillis() - startTime;

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

    System.out.println("\n\nReader Preference mode: \n\nreader_proportion,average_time");
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

  private static void runReaderPreferenceMode(
      List<DatabaseAccessor> databaseAccessors) {
    SemaphoreHelper.mode = READER_PREFERENCE;
    databaseAccessors.forEach(DatabaseAccessor::start);
  }

  private static void runBlockingMode(
      List<DatabaseAccessor> databaseAccessors) {
    SemaphoreHelper.mode = BLOCKING;
    databaseAccessors.forEach(DatabaseAccessor::start);
  }
}
