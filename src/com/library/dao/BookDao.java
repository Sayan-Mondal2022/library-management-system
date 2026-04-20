package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.BookDto;
import com.library.dto.BookSummaryDto;
import com.library.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDao {
    private BookDto setDetails(ResultSet res) throws SQLException {
        BookDto newBook = new BookDto();

        newBook.setIsbn(res.getString("isbn"));
        newBook.setTitle(res.getString("title"));
        newBook.setAuthorId(res.getInt("author_id"));
        newBook.setGenreId(res.getInt("genre_id"));
        newBook.setAuthorName(res.getString("author_name"));
        newBook.setGenreName(res.getString("genre_name"));
        newBook.setPublisher(res.getString("publisher"));
        newBook.setEdition(res.getString("edition"));

        int year = res.getInt("publication_year");
        newBook.setPublicationYear(res.wasNull() ? null : year);

        int pages = res.getInt("pages");
        newBook.setPages(res.wasNull() ? null : pages);

        newBook.setDescription(res.getString("description"));
        newBook.setLanguage(res.getString("language"));

        return newBook;
    }


    private List<BookDto> getBooksByCategory(String categoryType, String query) throws SQLException {
        String sql = """
                SELECT
                	t1.isbn, t1.title,
                    t1.author_id, t2.name AS author_name,
                    t1.genre_id, t3.name AS genre_name,
                    t1.publisher, t1.publication_year,
                    t1.edition, t1.pages,
                    t1.language, t1.description
                FROM Books AS t1 INNER JOIN Authors AS t2 ON t1.author_id = t2.id
                INNER JOIN Genres AS t3 ON t1.genre_id = t3.id
                WHERE t1.is_deleted = FALSE
                """;

        if (categoryType.equalsIgnoreCase("author"))
            sql = sql + "AND t1.author_id = ?;";
        else if (categoryType.equalsIgnoreCase("genre"))
            sql = sql + "AND t1.genre_id = ?;";
        else
            sql = sql + "AND t1.title = ?;";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (categoryType.equalsIgnoreCase("author"))
                ps.setInt(1, Integer.parseInt(query));
            else if (categoryType.equalsIgnoreCase("genre"))
                ps.setInt(1, Integer.parseInt(query));
            else
                ps.setString(1, query);

            List<BookDto> bookList = new ArrayList<>();
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    BookDto newBook = setDetails(res);
                    bookList.add(newBook);
                }
            } catch (SQLException e) {
                throw new SQLException("Failed to retrieve Books data", e);
            }
            return bookList;
        }
    }

    public List<BookDto> getBooksByAuthor(int authorId) throws SQLException {
        return getBooksByCategory("author", String.valueOf(authorId));
    }

    public List<BookDto> getBooksByGenre(int genreId) throws SQLException {
        return getBooksByCategory("genre", String.valueOf(genreId));
    }

    public List<BookDto> getBooksByTitle(String title) throws SQLException {
        return getBooksByCategory("title", title);
    }


    public BookDto getBookByIsbn(String isbn) throws SQLException {
        String sql = """
                SELECT
                	t1.isbn, t1.title,
                    t1.author_id, t2.name AS author_name,
                    t1.genre_id, t3.name AS genre_name,
                    t1.publisher, t1.publication_year,
                    t1.edition, t1.pages,
                    t1.language, t1.description
                FROM Books AS t1 INNER JOIN Authors AS t2 ON t1.author_id = t2.id
                INNER JOIN Genres AS t3 ON t1.genre_id = t3.id
                WHERE t1.is_deleted = FALSE AND t1.isbn = ?;
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next())
                    return setDetails(res);
                return null;

            }
        }
    }


    public void librarianAddBook(String insertType, Book book) throws SQLException {
        String sql = "INSERT INTO books (isbn, title, author_id, genre_id) VALUES (?, ?, ?, ?)";

        if (insertType.equalsIgnoreCase("full"))
            sql = """
                    INSERT INTO Books (isbn, title, author_id, genre_id, publisher, publication_year, edition, pages, language, description)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, book.getIsbn());
                ps.setString(2, book.getTitle());
                ps.setInt(3, book.getAuthorId());
                ps.setInt(4, book.getGenreId());

                if (insertType.equalsIgnoreCase("full")) {
                    ps.setString(5, book.getPublisher());
                    ps.setInt(6, book.getPublicationYear());
                    ps.setString(7, book.getEdition());
                    ps.setInt(8, book.getPages());
                    ps.setString(9, book.getLanguage());
                    ps.setString(10, book.getDescription());
                }
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed during Book Insertion", e);
            }

        }
    }

    public void librarianUpdateBook(Book book) throws SQLException {
        String sql = """
                UPDATE Books
                SET
                    title = ?,
                    author_id = ?,
                    genre_id = ?,
                    publisher = ?,
                    publication_year = ?,
                    pages = ?,
                    edition = ?,
                    language = ?,
                    description = ?
                WHERE isbn = ?;
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, book.getTitle());
                ps.setInt(2, book.getAuthorId());
                ps.setInt(3, book.getGenreId());
                ps.setString(4, book.getPublisher());

                if (book.getPublicationYear() != null)
                    ps.setInt(5, book.getPublicationYear());
                else
                    ps.setNull(5, java.sql.Types.INTEGER);

                if (book.getPages() != null)
                    ps.setInt(6, book.getPages());
                else
                    ps.setNull(6, java.sql.Types.INTEGER);

                ps.setString(7, book.getEdition());
                ps.setString(8, book.getLanguage());
                ps.setString(9, book.getDescription());


                ps.setString(10, book.getIsbn());

                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed while updating the book", e);
            }
        }
    }

    public void librarianDeleteBook(String isbn) throws SQLException {
        String sql = "UPDATE Books SET is_deleted = TRUE WHERE isbn = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, isbn);

                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed during the book update", e);
            }
        }
    }


    public List<BookDto> getBookIsbnTitle(boolean isDeleted) throws SQLException {
        String sql = "SELECT isbn, title FROM Books WHERE is_deleted = ?;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, isDeleted);

            List<BookDto> list = new ArrayList<>();
            try (ResultSet res = ps.executeQuery()){
                while (res.next()) {
                    BookDto book = new BookDto();

                    book.setIsbn(res.getString("isbn"));
                    book.setTitle(res.getString("title"));

                    list.add(book);
                }
            } catch (SQLException e) {
                throw new SQLException("Failed to retrieve the Book data",e);
            }
            return list;
        }
    }

    public List<BookDto> getAllBooks(boolean isDeleted) throws SQLException {
        String sql = """
                SELECT
                    t1.*,
                    t2.name AS author_name,
                    t3.name AS genre_name
                FROM Books t1
                JOIN Authors t2
                    ON t1.author_id = t2.id
                JOIN Genres t3
                    ON t1.genre_id = t3.id
                WHERE t1.is_deleted = ?;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, isDeleted);

            List<BookDto> bookList = new ArrayList<>();
            try (ResultSet res = ps.executeQuery()){
                while (res.next()) {
                    BookDto newBook = setDetails(res);
                    bookList.add(newBook);
                }
            } catch (SQLException e){
                throw new SQLException("Failed to retrieve Books data", e);
            }
            return bookList;

        }
    }

    public BookSummaryDto getBookSummaryByIsbn(String isbn) throws SQLException {
        String sql = """
                SELECT
                    t2.isbn,
                    t3.title,
                    t4.name AS author_name,
                    t5.name AS genre_name,
                    t3.publisher,
                    t3.publication_year,
                    t3.pages,
                    t3.edition,
                    t3.language,
                    t3.description,
                    t3.is_deleted,
                
                    GROUP_CONCAT(DISTINCT t7.name) AS section_names,
                    COUNT(t2.barcode) AS total_copies,
                
                    COUNT(
                        CASE
                            WHEN t1.return_date IS NULL THEN t1.borrow_id
                        END
                    ) AS total_copies_issued,
                
                    SUM(
                        CASE 
                            WHEN t2.book_status = 'AVAILABLE' THEN 1 
                            ELSE 0 
                        END
                    ) AS total_available_copies
                
                FROM BookItems t2
                LEFT JOIN BorrowedBooks t1 ON t1.barcode = t2.barcode
                INNER JOIN Books t3 ON t2.isbn = t3.isbn
                INNER JOIN Authors t4 ON t3.author_id = t4.id
                INNER JOIN Genres t5 ON t3.genre_id = t5.id
                LEFT JOIN Shelves t6 ON t2.shelf_id = t6.id
                LEFT JOIN Section t7 ON t6.section_id = t7.id
                WHERE t2.isbn = ?
                GROUP BY t2.isbn
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);

            try (ResultSet res = ps.executeQuery())
            {
                if (res.next()) {
                    BookSummaryDto dto = new BookSummaryDto();

                    dto.setIsbn(res.getString("isbn"));
                    dto.setTitle(res.getString("title"));
                    dto.setAuthorName(res.getString("author_name"));
                    dto.setGenreName(res.getString("genre_name"));

                    dto.setPublisher(res.getString("publisher"));
                    dto.setPublicationYear(res.getInt("publication_year"));
                    dto.setPages(res.getInt("pages"));
                    dto.setEdition(res.getString("edition"));
                    dto.setLanguage(res.getString("language"));
                    dto.setDescription(res.getString("description"));
                    dto.setDeleted(res.getBoolean("is_deleted"));

                    dto.setSectionNames(res.getString("section_names"));

                    dto.setTotalCopies(res.getInt("total_copies"));
                    dto.setTotalCopiesIssued(res.getInt("total_copies_issued"));
                    dto.setTotalAvailableCopies(res.getInt("total_available_copies"));

                    return dto;
                }
                return null;
            }
        }
    }
}