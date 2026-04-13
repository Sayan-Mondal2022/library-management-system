package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.BookItemDto;
import com.library.models.BookItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookItemDao {
    public void addBookItem(BookItem book) throws RuntimeException {
        String sql = "INSERT INTO BookItems (barcode, isbn, shelf_id, book_status, book_condition) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getBarcode());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getShelf_id());
            ps.setString(4, book.getBook_status().name());
            ps.setString(5, book.getBook_condition().name());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected < 0)
                throw new RuntimeException("Error occured while inserting the data!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // This function will fetch all the Copies available
    public List<BookItemDto> getAllBookCopies(boolean is_removed) throws RuntimeException {
        String sql = "SELECT t1.barcode, t1.isbn, t1.shelf_id, t3.name AS \"section_name\", t1.book_status, t1.book_condition\n" +
                "FROM bookitems AS t1 INNER JOIN shelves AS t2 ON t1.shelf_id = t2.id INNER JOIN section AS t3 ON t2.section_id = t3.id where is_removed = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, is_removed);

            ResultSet res = ps.executeQuery();

            List<BookItemDto> bookList = new ArrayList<>();

            while (res.next()) {
                BookItemDto dto = new BookItemDto();

                dto.setBarcode(res.getString("barcode"));
                dto.setIsbn(res.getString("isbn"));
                dto.setShelf_id(res.getString("shelf_id"));
                dto.setSection_name(res.getString("section_name"));
                dto.setStatus(res.getString("book_status"));
                dto.setCondition(res.getString("book_condition"));

                bookList.add(dto);
            }

            return bookList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public BookItemDto getBookItemByBarcode(String barcode) throws RuntimeException {
        String sql = "SELECT t1.barcode, t1.isbn, t1.shelf_id, t3.name AS \"section_name\", t1.book_status, t1.book_condition\n" +
                "FROM bookitems AS t1 INNER JOIN shelves AS t2 ON t1.shelf_id = t2.id INNER JOIN section AS t3 ON t2.section_id = t3.id where t1.barcode = ? AND is_removed = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, barcode);
            ps.setBoolean(2, false);
            ResultSet res = ps.executeQuery();

            if (!res.next())
                throw new RuntimeException("Book Item Not found!");


            BookItemDto dto = new BookItemDto();

            dto.setBarcode(res.getString("barcode"));
            dto.setIsbn(res.getString("isbn"));
            dto.setShelf_id(res.getString("shelf_id"));
            dto.setSection_name(res.getString("section_name"));
            dto.setStatus(res.getString("book_status"));
            dto.setCondition(res.getString("book_condition"));

            return dto;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBookItem(BookItem book){
        String sql = "UPDATE BookItems SET shelf_id = ?, book_status = ?, book_condition = ? WHERE barcode = ? AND isbn = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getShelf_id());
            ps.setString(2, book.getBook_status().name());
            ps.setString(3, book.getBook_condition().name());

            // WHERE clause (identifier)
            ps.setString(4, book.getBarcode());
            ps.setString(5, book.getIsbn());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0)
                throw new RuntimeException("No BookItem found to update!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
