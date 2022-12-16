package org.example.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

  public static void setup() throws SQLException {
    System.out.println("Setting up database" + databaseConnection());

  }

  @Bean
  public static Connection databaseConnection() throws SQLException {
    String url = "jdbc:postgresql://localhost/ep_db";
    Properties props = new Properties();
    props.setProperty("user", "ep_db_user");
    props.setProperty("password", "1234");
    return DriverManager.getConnection(url, props);
  }

}
