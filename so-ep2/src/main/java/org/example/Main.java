package org.example;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.example.database_accessors.DatabaseAccessor;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;

class Main {

  public static void main(String[] args) throws IOException {
    List<String> database = IOHelper.readDatabase();
    List<DatabaseAccessor> databaseAccessors = RandomnessHelper.getShuffledDatabaseAccessors(
        database, 90);

    logOriginalOrder(databaseAccessors);

    // Marcar inicio da execucao
    naiveApproach(databaseAccessors);
    // Marcar termino da execucao

    // Marcar inicio da execucao
    // TODO: Implement the parallel approach
    // Marcar termino da execucao

//    System.out.println(database);
  }

  private static void logOriginalOrder(
      List<DatabaseAccessor> databaseAccessors) {
    System.out.println("Original order: " + databaseAccessors.stream()
        .map(databaseAccessor -> Integer.toString(databaseAccessor.getIndex()))
        .collect(Collectors.joining(" ", "\n", "\n")));
  }

  private static void naiveApproach(List<DatabaseAccessor> databaseAccessors) {
    databaseAccessors.forEach(DatabaseAccessor::start);
  }


}
