package org.example;

import org.example.repository.DatabaseConfig;
import org.example.views.MainMenu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {
    var context = new AnnotationConfigApplicationContext(Config.class);
    DatabaseConfig.setup();
//    var mainMenu = context.getBean(MainMenu.class);
//    mainMenu.run();
  }
}
