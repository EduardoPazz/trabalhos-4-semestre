package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

  public static void main(String[] args) throws SQLException {
    testDBConnection();

//
//    var context = new AnnotationConfigApplicationContext(Config.class);
//    var mainMenu = context.getBean(MainMenu.class);
//    mainMenu.run();
  }

  private static void testDBConnection() throws SQLException {
    String url = "jdbc:postgresql://localhost/postgres?user=postgres&password=1234";
    Connection conn = DriverManager.getConnection(url);

    Statement st = conn.createStatement();
    st.execute("set search_path = \"4.12\";");
    ResultSet rs = st.executeQuery("SELECT * FROM aluno");
    while (rs.next()) {
      System.out.print("Column 1 returned ");
      System.out.println(rs.getString(1));
    }
    rs.close();
    st.close();
  }
}
