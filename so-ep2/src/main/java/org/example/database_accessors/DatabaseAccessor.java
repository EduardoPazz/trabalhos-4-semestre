package org.example.database_accessors;

import java.util.List;

public abstract class DatabaseAccessor extends Thread {
  protected final java.util.concurrent.Semaphore db;

  protected static final int MAX_RUNS = 5;
  protected int index;
  protected List<String> database;

  public DatabaseAccessor(int index, List<String> database) {
    this.index = index;
    this.database = database;
    db = new java.util.concurrent.Semaphore(1);
  }

  public int getIndex() {
    return index;
  }
}
