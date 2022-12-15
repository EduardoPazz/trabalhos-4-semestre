package org.example;

import org.example.views.MainMenu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    var context = new AnnotationConfigApplicationContext(Config.class);
    var mainMenu = context.getBean(MainMenu.class);
    mainMenu.run();
  }
}
