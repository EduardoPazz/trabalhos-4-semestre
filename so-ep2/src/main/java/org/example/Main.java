package org.example;

import java.util.ArrayList;
import java.util.List;
import org.example.database_clients.Writer;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;

import java.util.concurrent.ThreadLocalRandom;

class Main {

    public static void main(String[] args) throws InterruptedException {
//        RandomnessHelper.getShuffledReadersAndWriters(10);
//      List<String> database = IOHelper.readDatabase();

      Writer writer = new Writer();
      List<String> database = new ArrayList<>(List.of("a", "b", "c"));
      System.out.println("Database before: " + database);

      writer.setDatabase(database);
      writer.start();
      writer.join();

      System.out.println("Database after: " + database);

    }

}
