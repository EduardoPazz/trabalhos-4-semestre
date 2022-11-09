package org.example.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.example.database_accessors.DatabaseAccessor;
import org.example.database_accessors.Reader;
import org.example.database_accessors.Writer;

public class RandomnessHelper {

  private static final int MAX_CAPACITY = 100;

  public static List<DatabaseAccessor> getShuffledReadersAndWriters(
      int proportionOfReaders) {
    List<DatabaseAccessor> readersAndWriters = new ArrayList<>(MAX_CAPACITY);

    IntStream.range(0, proportionOfReaders)
        .forEach(i -> readersAndWriters.add(new Reader()));
    IntStream.range(proportionOfReaders, MAX_CAPACITY)
        .forEach(i -> readersAndWriters.add(new Writer()));

    Collections.shuffle(readersAndWriters);

    return readersAndWriters;
  }
}
