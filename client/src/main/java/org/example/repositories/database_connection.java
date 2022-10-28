package org.example.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.entities.Message;

public class database_connection {
    public database_connection() {
        Connection connection = null;

        try {
            
            connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  
        
            
            statement.executeUpdate("DROP TABLE IF EXISTS clientRepo");
            statement.executeUpdate("CREATE TABLE clientRepo (name STRING, email STRING, subject STRING), body STRING, date DATE");
    
            ResultSet rs = statement.executeQuery("SELECT * FROM clientRepo");
            while(rs.next()) {
            
                System.out.println("Email : " + rs.getString("email"));
                System.out.println("Date : " + rs.getInt("date"));
                System.out.println("Subject : " + rs.getString("subject"));
                System.out.println("Body : " + rs.getString("body"));
            }
        } catch(SQLException e) {
            
            System.err.println(e.getMessage());
        }

        finally {
            try {
                if(connection != null){
                    connection.close();
                }
            } catch(SQLException e) {
        
                System.err.println(e.getMessage());
            }
        }
    
    }

    public void addMessage(Message message){

        Connection connection = null;

        try {
            
            connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  
        
            
            statement.executeUpdate("INSERT INTO clientRepo VALUES (" + message.getFromAlias() + ", " + message.getFromDomain() + ", " + message.getSubject() + ", " + message.getBody() + ", " + message.getSendDate() + ")");

        } catch(SQLException e) {
            
            System.err.println(e.getMessage());
        }

        finally {
            try {
                if(connection != null){
                    connection.close();
                }
            } catch(SQLException e) {
        
                System.err.println(e.getMessage());
            }
        }
    }
    
}
      

