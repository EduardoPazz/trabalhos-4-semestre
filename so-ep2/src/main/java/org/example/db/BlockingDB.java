package org.example.db;

import java.util.LinkedList;
import java.util.List;

public class BlockingDB implements DB {

  private final List<String> list = new LinkedList<>();

  public BlockingDB(List<String> initialElements) {
    list.addAll(initialElements);
  }

  public synchronized void set(int index, String element) {
    list.set(index, element);
  }

  public synchronized String get(int index) {
    return list.get(index);
  }

  public int size() {
    return list.size();
  }
}
