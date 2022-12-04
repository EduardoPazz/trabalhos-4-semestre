package org.example.db;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Inspired by code examples from
 * https://www.codejava.net/java-core/concurrency/java-readwritelock-and-reentrantreadwritelock-example
 */
public class ReadPreferenceDB extends DB {

  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

  public ReadPreferenceDB(List<String> initialElements, ListImpl listImpl) {
    super(initialElements, listImpl);
  }

  public void set(int index, String element) {
    Lock writeLock = readWriteLock.writeLock();
    writeLock.lock();

    try {
      list.set(index, element);
    } finally {
      writeLock.unlock();
    }
  }

  public String get(int index) {
    Lock readLock = readWriteLock.readLock();
    readLock.lock();

    try {
      return list.get(index);
    } finally {
      readLock.unlock();
    }
  }

  public int size() {
    return list.size();
  }
}
