package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BookDao {
    public void librarianAddBook(Book book) throws Exception{
        // INSERT QUERY for BOOKS Table.
        String sql = "INSERT INTO books (isbn, title, author, genre) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getGenre());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Details have been added to the Database!!");
            } else {
                throw new SQLException("Error while inserting the Values");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void librarianUpdateBook(Book book) throws Exception{
        // Keeping one general UPDATE query for any change done to a particular book
        String sql = "UPDATE Books SET title = ?, author = ?, genre = ? WHERE isbn = ?";

        try (Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getIsbn());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book details has been updated.");
            } else {
                throw new RuntimeException("Book not found.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void librarianRemoveBook(int isbn) throws Exception{
        String sql = "UPDATE Books SET is_deleted = TRUE WHERE isbn = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, isbn);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book marked as deleted.");
            } else {
                throw new RuntimeException("Book not found.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void librarianShowALLBooks() throws Exception{
        String sql = "SELECT * FROM Books WHERE is_deleted=FALSE;";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book marked as deleted.");
            } else {
                System.out.println("Book not found.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void librarianShowDeletedBooks() throws Exception{

    }
}