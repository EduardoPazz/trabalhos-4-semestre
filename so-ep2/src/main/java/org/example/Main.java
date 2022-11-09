package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.database_accessors.DatabaseAccessor;
import org.example.database_accessors.Writer;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;

class Main {

  public static void main(String[] args)
      throws InterruptedException, IOException {
    List<DatabaseAccessor> readersAndWriters = RandomnessHelper.getShuffledReadersAndWriters(
        10);
    List<String> database2 = IOHelper.readDatabase();
    System.out.println(database2);

    Writer writer = new Writer();
    List<String> database = new ArrayList<>(List.of("a", "b", "c"));
    System.out.println("Database before: " + database);

    writer.setDatabase(database);
    writer.start();
    writer.join();

    System.out.println("Database after: " + database);

  }

}
