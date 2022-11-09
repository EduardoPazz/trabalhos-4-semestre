package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

class RandomnessHelper {
    private static final int MAX_CAPACITY = 100;
    static List<Runnable> getShuffledReadersAndWriters(int proportionOfReaders) {
        List<Runnable> readersAndWriters = new ArrayList<>(MAX_CAPACITY);

        IntStream.range(0, proportionOfReaders).forEach(i -> readersAndWriters.add(new Reader()));
        IntStream.range(proportionOfReaders, MAX_CAPACITY).forEach(i -> readersAndWriters.add(new Writer()));

        Collections.shuffle(readersAndWriters);

        return readersAndWriters;
    }
}
