package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseExample {
    // Database URL - this will create a database file named 'tutor.db' in your project directory
    private static final String DB_URL = "jdbc:sqlite:tutor.db";

    public static void main(String[] args) {
        try {
            // Create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to SQLite database!");

            // Create a table
            createTable(conn, "tutors");

            // Insert some data
            insertData(conn, "John Doe", "Mathematics");
            insertData(conn, "Jane Smith", "Physics");
            insertData(conn, "Kiran", "Science");

            // Query and display the data
            queryData(conn);

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createTable(Connection conn, String tableName) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, subject TEXT NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table " + tableName + " created successfully!");
        }
    }

    private static void insertData(Connection conn, String name, String subject) throws SQLException {
        String sql = "INSERT INTO tutors (name, subject) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, subject);
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully!");
        }
    }

    private static void queryData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM tutors";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\nTutors in the database:");
            System.out.println("ID\tName\t\tSubject");
            System.out.println("--------------------------------");
            
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                                 rs.getString("name") + "\t" +
                                 rs.getString("subject"));
            }
        }
    }

    private static void deleteTable(Connection conn, String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table " + tableName + " deleted successfully!");
        }
    }
} 