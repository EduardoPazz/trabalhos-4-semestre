package org.example.database_accessors;

import java.util.List;

public abstract class DatabaseAccessor extends Thread {

  protected static final int MAX_RUNS = 1;

  protected List<String> database;

  public void setDatabase(final List<String> database) {
    this.database = database;
  }
}
