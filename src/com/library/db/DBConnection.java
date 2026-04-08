package com.library.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Load the .env file
    private static final Dotenv dotenv = Dotenv.load();

    // Fetch the values using the keys you defined in the .env file
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("USER");
    private static final String PASS = dotenv.get("DB_PASS");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}