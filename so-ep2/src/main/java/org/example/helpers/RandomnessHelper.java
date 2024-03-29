package org.example.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.example.database_accessors.DatabaseAccessor;
import org.example.database_accessors.Reader;
import org.example.database_accessors.Writer;
import org.example.db.DB;

public class RandomnessHelper {

  private static final int MAX_CAPACITY = 100;

  public static List<DatabaseAccessor> getShuffledDatabaseAccessors(DB database,
      int proportionOfReaders) {
    List<DatabaseAccessor> databaseAccessors = new ArrayList<>(MAX_CAPACITY);

    IntStream.range(0, proportionOfReaders)
        .forEach(i -> databaseAccessors.add(new Reader(i, database)));
    IntStream.range(proportionOfReaders, MAX_CAPACITY)
        .forEach(i -> databaseAccessors.add(new Writer(i, database)));

    Collections.shuffle(databaseAccessors);

    return databaseAccessors;
  }
}
