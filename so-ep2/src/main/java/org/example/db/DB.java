package org.example.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class DB {

  protected final List<String> list;

  public DB(List<String> initialElements, ListImpl listImpl) {
    list = switch (listImpl) {
      case LINKED_LIST -> new LinkedList<>();
      case ARRAY_LIST -> new ArrayList<>();
    };
    list.addAll(initialElements);
  }

  public abstract void set(int index, String element);

  public abstract String get(int index);

  public abstract int size();

  public enum ListImpl {
    LINKED_LIST, ARRAY_LIST
  }
}
