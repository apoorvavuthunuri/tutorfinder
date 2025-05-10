package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final String DB_URL = "jdbc:sqlite:tutor.db";

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Create table
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS tutors (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "subject TEXT NOT NULL)");
            }

            // Insert sample data
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("INSERT OR IGNORE INTO tutors (name, subject) VALUES " +
                    "('John Doe', 'Mathematics')," +
                    "('Jane Smith', 'Physics')," +
                    "('Alice Johnson', 'Chemistry')," +
                    "('Bob Wilson', 'Computer Science')," +
                    "('Sarah Brown', 'English')");
            }
        }
    }
} 