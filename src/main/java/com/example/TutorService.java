package com.example;

import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    private static final String DB_URL = "jdbc:sqlite:tutor.db";

    public TutorService() {
        try {
            // Create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to SQLite database!");

            deleteTable(conn, "tutors");
            createTable(conn, "tutors");

            // Insert some data
            insertData(conn, "Hermoine Doe", "Mathematics");
            insertData(conn, "Harry Smith", "Physics");
            insertData(conn, "Jeffrey Johnson", "Biology");
            insertData(conn, "Mary Ludwig", "Chemistry");

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteTable(Connection conn, String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table " + tableName + " deleted successfully!");
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

    public List<Tutor> getAllTutors() {
        System.out.println("Attempting to fetch all tutors...");
        List<Tutor> tutors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            System.out.println("Database connection established");
            try (Statement stmt = conn.createStatement()) {
                System.out.println("Executing query: SELECT * FROM tutors");
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM tutors")) {
                    while (rs.next()) {
                        Tutor tutor = new Tutor();
                        tutor.setId(rs.getLong("id"));
                        tutor.setName(rs.getString("name"));
                        tutor.setSubject(rs.getString("subject"));
                        // Add some sample data for the UI
                        tutor.setRating(4.5);
                        tutor.setHourlyRate(50.0);
                        tutor.setDescription("Experienced tutor with a passion for teaching " + rs.getString("subject"));
                        tutors.add(tutor);
                        System.out.println("Added tutor: " + tutor.getName());
                    }
                }
            }
            System.out.println("Successfully fetched " + tutors.size() + " tutors");
        } catch (SQLException e) {
            System.err.println("Error in getAllTutors: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching tutors from database", e);
        }
        return tutors;
    }

    public Optional<Tutor> getTutorById(Long tutorId) {
        String sql = "SELECT * FROM tutors WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, tutorId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getLong("id"));
                tutor.setName(rs.getString("name"));
                tutor.setSubject(rs.getString("subject"));
                return Optional.of(tutor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tutor by ID", e);
        }
        return Optional.empty();
    }

    public List<Tutor> getTutorsBySubject(String subject) {
        List<Tutor> tutors = new ArrayList<>();
        String sql = "SELECT * FROM tutors WHERE subject = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getLong("id"));
                tutor.setName(rs.getString("name"));
                tutor.setSubject(rs.getString("subject"));
                tutors.add(tutor);
            }
            if (tutors.isEmpty()) {
                throw new NoTutorsFoundException("No tutors found for subject: " + subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tutors by subject", e);
        }
        return tutors;
    }
} 