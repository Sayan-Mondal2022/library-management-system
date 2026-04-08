package com.library.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import io.github.cdimascio.dotenv.Dotenv; // Import the library

public class DBConnection {
    // Load the .env file
//    private static final Dotenv dotenv = Dotenv.load();

    // Fetch the values using the keys you defined in the .env file
//    private static final String URL = dotenv.get("DB_URL");
//    private static final String USER = dotenv.get("DB_USER");
//    private static final String PASS = dotenv.get("DB_PASS");

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/library";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "Sayan123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}