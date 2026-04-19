package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.BookDto;
import com.library.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BookDao {
    private BookDto setDetails(ResultSet res) throws RuntimeException {
        try {
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDto> getBooksByAuthor(int authorId) throws RuntimeException {
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
                WHERE t1.is_deleted = FALSE AND t1.author_id = ?;
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, authorId);

            ResultSet res = ps.executeQuery();
            List<BookDto> bookList = new ArrayList<>();

            while (res.next()) {
                BookDto newBook = setDetails(res);
                bookList.add(newBook);
            }
            return bookList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDto> getBooksByGenre(int genreId) throws RuntimeException {
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
                WHERE t1.is_deleted = FALSE AND t1.genre_id = ?;
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, genreId);

            ResultSet res = ps.executeQuery();
            List<BookDto> bookList = new ArrayList<>();

            while (res.next()) {
                BookDto newBook = setDetails(res);
                bookList.add(newBook);
            }
            return bookList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
     public List<BookDto> getBooksByTitle(String title) throws RuntimeException {
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
                WHERE t1.is_deleted = FALSE AND t1.title = ?;
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);

            ResultSet res = ps.executeQuery();
            List<BookDto> bookList = new ArrayList<>();

            while (res.next()) {
                BookDto newBook = setDetails(res);
                bookList.add(newBook);
            }
            return bookList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public BookDto getBookByIsbn(String isbn) {
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


            ResultSet res = ps.executeQuery();
            res.next();
            return setDetails(res);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void librarianAddBook(String insert_type, Book book) throws Exception {
        String sql = "INSERT INTO books (isbn, title, author_id, genre_id) VALUES (?, ?, ?, ?)";

        if (insert_type.equalsIgnoreCase("full"))
            sql = "INSERT INTO Books (isbn, title, author_id, genre_id, publisher, publication_year, edition, pages, language, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setInt(3, book.getAuthorId());
            ps.setInt(4, book.getGenreId());

            if (insert_type.equalsIgnoreCase("full")) {
                ps.setString(5, book.getPublisher());
                ps.setInt(6, book.getPublicationYear());
                ps.setString(7, book.getEdition());
                ps.setInt(8, book.getPages());
                ps.setString(9, book.getLanguage());
                ps.setString(10, book.getDescription());
            }
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void librarianUpdateBook(Book book) throws RuntimeException {
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

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void librarianDeleteBook(String isbn) throws RuntimeException {
        String sql = "UPDATE Books SET is_deleted = TRUE WHERE isbn = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, isbn);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<BookDto> getBookIsbnTitle(boolean is_deleted) throws RuntimeException {
        String sql = "SELECT isbn, title FROM Books WHERE is_deleted = ?;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, is_deleted);


            List<BookDto> list = new ArrayList<>();
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                BookDto book = new BookDto();

                book.setIsbn(res.getString("isbn"));
                book.setTitle(res.getString("title"));

                list.add(book);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDto> getAllBooks(boolean is_deleted) throws RuntimeException {
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
            ps.setBoolean(1, is_deleted);


            List<BookDto> bookList = new ArrayList<>();
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                BookDto newBook = setDetails(res);
                bookList.add(newBook);
            }
            return bookList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}