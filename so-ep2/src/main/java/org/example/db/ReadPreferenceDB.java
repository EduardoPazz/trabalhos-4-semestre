package org.example.db;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Inspired by code examples from
 * https://www.codejava.net/java-core/concurrency/java-readwritelock-and-reentrantreadwritelock-example
 */
public class ReadPreferenceDB implements DB {

  private final List<String> list = new LinkedList<>();
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

  public ReadPreferenceDB(List<String> initialElements) {
    list.addAll(initialElements);
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
