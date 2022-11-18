package org.example;

import java.io.IOException;
import java.util.List;
import org.example.database_accessors.DatabaseAccessor;
import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;

class Main {

  public static void main(String[] args) throws IOException {
    List<String> database = IOHelper.readDatabase();
    List<DatabaseAccessor> databaseAccessors = RandomnessHelper.getShuffledDatabaseAccessors(
        database, 10);

    databaseAccessors.forEach(DatabaseAccessor::start);

  }


}
