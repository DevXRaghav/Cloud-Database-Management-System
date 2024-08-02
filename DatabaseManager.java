package com.example;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://<YOUR_RDS_ENDPOINT>:3306/cloud_db";
    private static final String USER = "dbuser";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertUser(String name, String email) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }

    public static void fetchUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Email: %s%n",
                                  rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        }
    }

    public static void main(String[] args) {
        try {
            insertUser("John Doe", "john.doe@example.com");
            fetchUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
