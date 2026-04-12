package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.BookItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookItemDao {
    // Accessible to all
    public List<BookItem> ShowBooks(String query_type, String query) throws Exception{
        String sql = "SELECT * FROM BookItems";

        if (query_type.equalsIgnoreCase("isbn"))
            sql = "SELECT * FROM BookItems WHERE isbn = ?";
        else if (query_type.equalsIgnoreCase("shelf_id"))
            sql = "SELECT * FROM BookItems WHERE LOWER(shelf_id) = ?";
        else if (query_type.equalsIgnoreCase("section"))
            sql = "SELECT * FROM BookItems WHERE LOWER(section) = ?";
        else if (query_type.equalsIgnoreCase("status"))
            sql = "SELECT * FROM BookItems WHERE status = ?";


        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){

            if (query_type.equalsIgnoreCase("isbn"))
                ps.setInt(1, Integer.parseInt(query));
            else if (!query_type.isEmpty())
                ps.setString(1, query);

            ResultSet res = ps.executeQuery();

            List<BookItem> bookItems = new ArrayList<>();

            while (res.next()){
                bookItems.add(new BookItem(
                        res.getString("barcode"),
                        res.getInt("isbn"),
                        res.getString("shelf_id"),
                        res.getString("section"),
                        res.getString("status")
                ));
            }

            return bookItems;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void ShowBooks(int isbn) {
        String sql = "SELECT * FROM BookItems WHERE isbn = ?";
    }

    public void ShowBooks(String status) {
        String sql = "SELECT * FROM BookItems WHERE status = ?";
    }

    public void ShowBooks(int isbn, String status) {
        String sql = "SELECT * FROM BookItems WHERE isbn = ? AND status = ?";
    }

    public void showBooksInShelf(int shelf_id) {
        String sql = "SELECT * FROM BookItems WHERE shelf_id = ?";
    }

    // Only accessible to librarian
    public void librarianAddBook(BookItem book_item) throws Exception{
        String sql = "INSERT INTO BookItem (barcode, isbn, shelf_id, section, book_status) VALUES (?, ?, ?, ?, ?);";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){

            // Inserting all the values:
            ps.setString(1, book_item.getBarcode());
            ps.setInt(2, book_item.getIsbn());
            ps.setString(3, book_item.getShelf_id());
            ps.setString(4, book_item.getSection());
            ps.setString(5, book_item.getStatus());

            ps.executeUpdate();

            System.out.println("\nBook Item has been added to Database!");

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void librarianMoveBook(){
        // This function will move a particular set of books from one shelf to another.
        // From one Section to Another.
    }

    public void librarianRemoveBook(){
        // This function will remove only a book with a Barcode.
        // If removed change the bookItem status to removed=True.
    }
}
