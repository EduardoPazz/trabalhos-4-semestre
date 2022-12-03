package org.example.db;

public interface DB {

  void set(int index, String element);

  String get(int index);

  int size();
}
