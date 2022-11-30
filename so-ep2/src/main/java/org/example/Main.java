package org.example;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.example.database_accessors.DatabaseAccessor;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;
import org.example.helpers.SemaphoreHelper;

class Main {

  public static void main(String[] args) throws IOException {
    List<String> database = IOHelper.readDatabase();
    List<DatabaseAccessor> databaseAccessors = RandomnessHelper.getShuffledDatabaseAccessors(
        database, 90);

    logOriginalOrder(databaseAccessors);

    // Marcar inicio da execucao
    blockingApproach(databaseAccessors);
    // Marcar termino da execucao

    // Marcar inicio da execucao
    readerPreferenceApproach(databaseAccessors);
    // Marcar termino da execucao

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
