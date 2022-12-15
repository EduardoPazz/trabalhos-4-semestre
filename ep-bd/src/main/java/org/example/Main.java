package org.example;

import org.example.view.View;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    var context = new AnnotationConfigApplicationContext(Config.class);
    var view = context.getBean(View.class);
    view.mainMenu();
  }
}
