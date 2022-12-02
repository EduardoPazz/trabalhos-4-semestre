package org.example.database_accessors;

import java.util.List;

public abstract class DatabaseAccessor extends Thread {
  protected static final int MAX_RUNS = 100;
  protected int index;
  protected List<String> database;

  public DatabaseAccessor(int index, List<String> database) {
    this.index = index;
    this.database = database;
  }

  public int getIndex() {
    return index;
  }
}
