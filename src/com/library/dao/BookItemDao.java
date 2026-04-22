package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.BookItemDto;
import com.library.models.Book;
import com.library.models.BookItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookItemDao {
    private BookItemDto setBookDetails(ResultSet res) throws RuntimeException {
        try {
            BookItemDto dto = new BookItemDto();

            dto.setBarcode(res.getString("barcode"));
            dto.setIsbn(res.getString("isbn"));
            dto.setTitle(res.getString("title"));
            dto.setAuthorName(res.getString("author_name"));
            dto.setGenreName(res.getString("genre_name"));
            dto.setShelfId(res.getString("shelf_id"));
            dto.setSectionName(res.getString("section_name"));
            dto.setStatus(res.getString("book_status"));
            dto.setCondition(res.getString("book_condition"));

            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addBookItem(BookItem book) throws RuntimeException {
        String sql = """
                INSERT INTO
                BookItems (barcode, isbn, shelf_id, book_status, book_condition)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getBarcode());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getShelfId());
            ps.setString(4, book.getBookStatus().name());
            ps.setString(5, book.getBookCondition().name());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected < 0)
                throw new RuntimeException("ERROR OCCURRED WHILE INSERTING THE DATA!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<BookItemDto> getAllCopies(boolean isRemoved) throws RuntimeException {
        String sql = """
                SELECT
                    t1.barcode,
                    t1.isbn,
                    t4.title,
                    t5.name AS author_name,
                    t6.name AS genre_name,
                    t1.shelf_id,
                    t3.name AS section_name,
                    t1.book_status,
                    t1.book_condition
                FROM BookItems AS t1
                LEFT JOIN Shelves AS t2 ON t1.shelf_id = t2.id
                LEFT JOIN Section AS t3 ON t2.section_id = t3.id
                INNER JOIN Books AS t4 ON t1.isbn = t4.isbn
                LEFT JOIN Authors AS t5 ON t4.author_id = t5.id
                LEFT JOIN Genres AS t6 ON t4.genre_id = t6.id
                WHERE t1.is_removed = ?;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, isRemoved);

            ResultSet res = ps.executeQuery();

            List<BookItemDto> bookList = new ArrayList<>();
            while (res.next()) {
                bookList.add(setBookDetails(res));
            }

            return bookList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public BookItemDto getBookItemByBarcode(String barcode) throws RuntimeException {
        String sql = """
                SELECT
                	t1.barcode,
                	t1.isbn,
                	t4.title,
                    t5.name AS author_name,
                    t6.name AS genre_name,
                    t1.shelf_id,
                    t3.name AS section_name,
                    t1.book_status,
                    t1.book_condition
                FROM BookItems AS t1 INNER JOIN Shelves AS t2 ON t1.shelf_id = t2.id
                INNER JOIN Section AS t3 ON t2.section_id = t3.id
                INNER JOIN Books AS t4 ON t1.isbn = t4.isbn
                INNER JOIN Authors AS t5 ON t4.author_id = t5.id
                INNER JOIN Genres AS t6 ON t4.genre_id = t6.id
                WHERE is_removed = FALSE AND t1.barcode = ?;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, barcode);
            ResultSet res = ps.executeQuery();

            if (!res.next())
                throw new RuntimeException("Book Item Not found!");
            return setBookDetails(res);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBookItem(BookItem book) {
        String sql = """
                UPDATE BookItems
                SET
                    shelf_id = ?,
                    book_status = ?,
                    book_condition = ?
                WHERE barcode = ? AND isbn = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getShelfId());
            ps.setString(2, book.getBookStatus().name());
            ps.setString(3, book.getBookCondition().name());

            ps.setString(4, book.getBarcode());
            ps.setString(5, book.getIsbn());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0)
                throw new RuntimeException("No BookItem found to update!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteBookItem(String barcode) {
        String sql = """
                UPDATE BookItems
                SET
                    is_removed = TRUE
                WHERE barcode = ?;
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, barcode);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0)
                throw new RuntimeException("No BookItem Found To DELETE!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getBookStatus(String barcode) throws RuntimeException {
        String sql = "SELECT IF(book_status = \"AVAILABLE\", 1, 0) AS status FROM BookItems WHERE barcode = ? ;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, barcode);

            ResultSet res = ps.executeQuery();
            res.next();

            return res.getInt("status");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private List<BookItemDto> getBookCopiesByAttribute(String attribute, String query) throws SQLException {
        String sql = """
                SELECT
                    t1.barcode,
                    t1.isbn,
                    t4.title,
                    t5.name AS author_name,
                    t6.name AS genre_name,
                    t1.shelf_id,
                    t3.name AS section_name,
                    t1.book_status,
                    t1.book_condition
                FROM BookItems AS t1
                LEFT JOIN Shelves AS t2 ON t1.shelf_id = t2.id
                LEFT JOIN Section AS t3 ON t2.section_id = t3.id
                INNER JOIN Books AS t4 ON t1.isbn = t4.isbn
                LEFT JOIN Authors AS t5 ON t4.author_id = t5.id
                LEFT JOIN Genres AS t6 ON t4.genre_id = t6.id
                WHERE t1.is_removed = False
                """;

        if (attribute.equalsIgnoreCase("status"))
            sql = sql + "AND t1.book_status = ?;";
        else
            sql = sql + "AND t1.book_condition = ?;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, query);
            ResultSet res = ps.executeQuery();

            List<BookItemDto> books = new ArrayList<>();
            while (res.next()) {
                books.add(setBookDetails(res));
            }
            return books;

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    public List<BookItemDto> getCopiesByStatus(String bookStatus) throws SQLException {
        return getBookCopiesByAttribute("status", bookStatus);
    }


    public List<BookItemDto> getCopiesByCondition(String bookCondition) throws SQLException {
        return getBookCopiesByAttribute("condition", bookCondition);
    }


    // This function is used internally to get books by Author, genre and Section name.
    private List<BookItemDto> getBookCopiesByCategory(String categoryType, String name, String bookStatus) throws SQLException {
        String sql = """
                SELECT
                    t1.barcode,
                    t1.isbn,
                    t4.title,
                    t5.name AS author_name,
                    t6.name AS genre_name,
                    t1.shelf_id,
                    t3.name AS section_name,
                    t1.book_status,
                    t1.book_condition
                FROM BookItems AS t1
                LEFT JOIN Shelves AS t2 ON t1.shelf_id = t2.id
                LEFT JOIN Section AS t3 ON t2.section_id = t3.id
                INNER JOIN Books AS t4 ON t1.isbn = t4.isbn
                LEFT JOIN Authors AS t5 ON t4.author_id = t5.id
                LEFT JOIN Genres AS t6 ON t4.genre_id = t6.id
                WHERE t1.is_removed = False AND t1.book_status = ?
                """;

        if (categoryType.equalsIgnoreCase("genre")) {
            sql = sql + "HAVING genre_name = ?";
        } else if (categoryType.equalsIgnoreCase("section")) {
            sql = sql + "HAVING section_name = ?";
        } else {
            sql = sql + "HAVING author_name = ?";
        }
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bookStatus);
            ps.setString(2, name);
            ResultSet res = ps.executeQuery();

            List<BookItemDto> books = new ArrayList<>();
            while (res.next()) {
                books.add(setBookDetails(res));
            }
            return books;

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    public List<BookItemDto> getCopiesByGenreName(String genreName, String bookStatus) throws SQLException {
        return getBookCopiesByCategory("genre", genreName, bookStatus);
    }


    public List<BookItemDto> getCopiesBySectionName(String sectionName, String bookStatus) throws SQLException {
        return getBookCopiesByCategory("section", sectionName, bookStatus);
    }

    public List<BookItemDto> getCopiesByAuthorName(String authorName, String bookStatus) throws SQLException {
        return getBookCopiesByCategory("author", authorName, bookStatus);
    }


    public void borrowBook(int userId, String barcode) throws SQLException{
        String sql = "INSERT INTO BorrowBookApplicants (user_id, barcode) VALUES (?, ?);";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setString(2, barcode);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0)
                    throw new SQLException("Failed to insert, no rows affected");
                else
                    con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed, to insert applicant data", e);

            }
        }
    }
}
