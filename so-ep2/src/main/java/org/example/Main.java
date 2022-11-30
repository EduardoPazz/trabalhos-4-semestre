package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.example.database_accessors.DatabaseAccessor;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;
import org.example.helpers.SemaphoreHelper;

class Main {

  public static void main(String[] args) throws IOException {
    List<String> database = IOHelper.readDatabase();

    int MAX_PROPORTIONS = 10;
    int MAX_LOOPS = 2;

    long[][] blockingAproachArrayResults = new long[MAX_PROPORTIONS][MAX_LOOPS];
    long[][] readerPreferenceApproachArrayResults = new long[MAX_PROPORTIONS][MAX_LOOPS];


    for(int i = 0; i < MAX_PROPORTIONS;i++){

      for(int j = 0; j < MAX_LOOPS; j++) {
        System.out.println("Proporção " + i + "Execução: " + j);
        List<String> localDatabase = new ArrayList<>(database);

        List<DatabaseAccessor> databaseAccessorsBlockingApproach = RandomnessHelper.getShuffledDatabaseAccessors(
            database, i);

        //logOriginalOrder(databaseAccessorsBlockingApproach);

        long startTime = System.currentTimeMillis();
        blockingApproach(databaseAccessorsBlockingApproach);

        databaseAccessorsBlockingApproach.forEach(x -> {
          try {
            x.join();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });

        long delta = System.currentTimeMillis() - startTime;

        blockingAproachArrayResults[i][j] = delta;


        List<DatabaseAccessor> databaseAccessorsReaderPreferenceApproachArrayResults= RandomnessHelper.getShuffledDatabaseAccessors(
            database, i);

        startTime = System.currentTimeMillis();
        readerPreferenceApproach(databaseAccessorsReaderPreferenceApproachArrayResults);

        databaseAccessorsReaderPreferenceApproachArrayResults.forEach(x -> {
          try {
            x.join();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });

        delta = System.currentTimeMillis() - startTime;

        readerPreferenceApproachArrayResults[i][j] = delta;
      }
    }

    System.out.println("Blocking aproatch: ");
    for(int i = 0; i < MAX_PROPORTIONS;i++)
    {
      System.out.println(i + ";" + Arrays.stream(blockingAproachArrayResults[i]).average().getAsDouble());
    }

    System.out.println();
    System.out.println();
    System.out.println("Reader preference aproatch: ");
    for(int i = 0; i < MAX_PROPORTIONS;i++)
    {
      System.out.println(i + ";" + Arrays.stream(readerPreferenceApproachArrayResults[i]).average().getAsDouble());
    }

//    System.out.println(database);
  }

  private static void logOriginalOrder(
      List<DatabaseAccessor> databaseAccessors) {
    System.out.println("Original order: " + databaseAccessors.stream()
        .map(databaseAccessor -> Integer.toString(databaseAccessor.getIndex()))
        .collect(Collectors.joining(" ", "\n", "\n")));
  }

  private static void readerPreferenceApproach(
      List<DatabaseAccessor> databaseAccessors) {
    SemaphoreHelper.mode = SemaphoreHelper.Mode.READER_PREFERENCE;
    databaseAccessors.forEach(DatabaseAccessor::start);
  }

  private static void blockingApproach(
      List<DatabaseAccessor> databaseAccessors) {
    SemaphoreHelper.mode = SemaphoreHelper.Mode.BLOCKING;
    databaseAccessors.forEach(DatabaseAccessor::start);
  }


}
