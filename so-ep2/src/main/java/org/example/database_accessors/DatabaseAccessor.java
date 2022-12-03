package org.example.database_accessors;

import org.example.db.DB;

public abstract class DatabaseAccessor extends Thread {

  protected static final int MAX_RUNS = 100;
  protected int index;
  protected DB database;

  public DatabaseAccessor(int index, DB database) {
    this.index = index;
    this.database = database;
  }
}
