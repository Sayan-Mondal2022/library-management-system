package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookDao {
    public void insertBook(Book book) {
        // INSERT QUERY for BOOKS Table.
        String sql = "INSERT INTO books (isbn, title, author, genre) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getGenre());
            pstmt.executeUpdate();

            System.out.println("Database Updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}