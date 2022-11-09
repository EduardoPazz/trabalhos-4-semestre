package org.example.database_clients;

import java.util.List;

abstract class DatabaseClient extends Thread {
  protected static final int MAX_RUNS = 1;

  protected List<String> database;

  public void setDatabase(final List<String> database) {
    this.database = database;
  }
}
