package org.example.db;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingDB implements DB {

  private final ReentrantLock lock = new ReentrantLock(true);
  private final List<String> list = new LinkedList<>();

  public BlockingDB(List<String> initialElements) {
    list.addAll(initialElements);
  }

  public void set(int index, String element) {
    lock.lock();
    try {
      list.set(index, element);
    } finally {
      lock.unlock();
    }
  }

  public String get(int index) {
    lock.lock();
    try {
      return list.get(index);
    } finally {
      lock.unlock();
    }
  }

  public int size() {
    return list.size();
  }
}
