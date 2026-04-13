package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.BookResponseDto;
import com.library.models.Book;
import com.library.models.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDao {
    public List<Genre> getAllGenres() throws Exception {
        String sql = "SELECT * FROM Genres";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            List<Genre> genres = new ArrayList<>();

            while (res.next()) {
                Genre genre = new Genre();

                genre.setGenre_id(res.getInt("id"));
                genre.setGenre_name(res.getString("name"));

                genres.add(genre);
            }

            return genres;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Book getBookByIsbn(String isbn) {
        String sql = "SELECT t1.*, \n" +
                "       t2.name AS author_name,\n" +
                "       t3.name AS genre_name\n" +
                "FROM Books t1\n" +
                "JOIN Authors t2 ON t1.author_id = t2.id\n" +
                "JOIN Genres t3 ON t1.genre_id = t3.id\n" +
                "WHERE t1.is_deleted = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, false);


            List<Book> bookList = new ArrayList<>();
            ResultSet res = ps.executeQuery();
            res.next();

            Book newBook = new Book();

            newBook.setIsbn(res.getString("isbn"));
            newBook.setTitle(res.getString("title"));
            newBook.setAuthor_id(res.getInt("author_id"));
            newBook.setGenre_id(res.getInt("genre_id"));
            newBook.setAuthor_name(res.getString("author_name"));
            newBook.setGenre_name(res.getString("genre_name"));
            newBook.setPublisher(res.getString("publisher"));
            newBook.setEdition(res.getString("edition"));

            int year = res.getInt("publication_year");
            newBook.setPublication_year(res.wasNull() ? null : year);

            int pages = res.getInt("pages");
            newBook.setPages(res.wasNull() ? null : pages);

            newBook.setDescription(res.getString("description"));
            newBook.setLanguage(res.getString("language"));

            return newBook;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Need to work on this class
    public List<Book> getBooksByQuery(String query_type, String query, boolean is_deleted) throws Exception {
        String sql;

        if (query_type.equalsIgnoreCase("isbn"))
            sql = "SELECT * FROM Books WHERE isbn = ? AND is_deleted = ?";
        else if (query_type.equalsIgnoreCase("title"))
            sql = " SELECT * FROM Books WHERE LOWER(title) = ? AND is_deleted = ?";
        else
            sql = "SELECT * FROM Books WHERE is_deleted = ?";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

//            if (query_type.equalsIgnoreCase())
            ps.setString(1, query);
            ps.setBoolean(2, is_deleted);


            List<Book> bookList = new ArrayList<>();
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Book newBook = new Book();

                newBook.setIsbn(res.getString("isbn"));
                newBook.setTitle(res.getString("title"));
                newBook.setAuthor_id(res.getInt("author_id"));
                newBook.setGenre_id(res.getInt("genre_id"));
                newBook.setPublisher(res.getString("publisher"));
                newBook.setEdition(res.getString("edition"));

                int year = res.getInt("publication_year");
                newBook.setPublication_year(res.wasNull() ? null : year);

                int pages = res.getInt("pages");
                newBook.setPages(res.wasNull() ? null : pages);

                newBook.setDescription(res.getString("description"));
                newBook.setLanguage(res.getString("language"));

                bookList.add(newBook);
            }
            return bookList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void librarianAddBook(String insert_type, Book book) throws Exception {
        // INSERT QUERY for BOOKS Table.
        String sql = "INSERT INTO books (isbn, title, author_id, genre_id) VALUES (?, ?, ?, ?)";

        if (insert_type.equalsIgnoreCase("full"))
            sql = "INSERT INTO Books (isbn, title, author_id, genre_id, publisher, publication_year, edition, pages, language, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setInt(3, book.getAuthor_id());
            ps.setInt(4, book.getGenre_id());

            if (insert_type.equalsIgnoreCase("full")) {
                ps.setString(5, book.getPublisher());
                ps.setInt(6, book.getPublication_year());
                ps.setString(7, book.getEdition());
                ps.setInt(8, book.getPages());
                ps.setString(9, book.getLanguage());
                ps.setString(10, book.getDescription());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Details have been added to the Database!!");
            } else {
                throw new SQLException("Error while inserting the Values");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void librarianUpdateBook(Book book) throws Exception {
        // Keeping one general UPDATE query for any change done to a particular book
        String sql = "UPDATE Books SET " +
                "title = ?, " +
                "author_id = ?, " +
                "genre_id = ?, " +
                "publisher = ?, " +
                "publication_year = ?, " +
                "pages = ?, " +
                "edition = ?, " +
                "language = ?, " +
                "description = ? " +
                "WHERE isbn = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor_id());
            ps.setInt(3, book.getGenre_id());

            ps.setString(4, book.getPublisher());

            // Handle NULL for Integer fields
            if (book.getPublication_year() != null)
                ps.setInt(5, book.getPublication_year());
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

    public void librarianRemoveBook(String isbn) throws Exception {
        String sql = "UPDATE Books SET is_deleted = TRUE WHERE isbn = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, isbn);
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


    public List<BookResponseDto> showBookIsbnTitle(boolean is_deleted) throws RuntimeException{
        String sql = "SELECT isbn, title FROM Books WHERE is_deleted = ?;";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, is_deleted);


            List<BookResponseDto> list = new ArrayList<>();
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                BookResponseDto book = new BookResponseDto();

                book.setIsbn(res.getString("isbn"));
                book.setTitle(res.getString("title"));

                list.add(book);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> showALLBooks(boolean is_deleted) throws Exception {
        String sql = "SELECT t1.*, \n" +
                "       t2.name AS author_name,\n" +
                "       t3.name AS genre_name\n" +
                "FROM Books t1\n" +
                "JOIN Authors t2 ON t1.author_id = t2.id\n" +
                "JOIN Genres t3 ON t1.genre_id = t3.id\n" +
                "WHERE t1.is_deleted = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, is_deleted);


            List<Book> bookList = new ArrayList<>();
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Book newBook = new Book();

                newBook.setIsbn(res.getString("isbn"));
                newBook.setTitle(res.getString("title"));
                newBook.setAuthor_id(res.getInt("author_id"));
                newBook.setGenre_id(res.getInt("genre_id"));
                newBook.setAuthor_name(res.getString("author_name"));
                newBook.setGenre_name(res.getString("genre_name"));
                newBook.setPublisher(res.getString("publisher"));
                newBook.setEdition(res.getString("edition"));

                int year = res.getInt("publication_year");
                newBook.setPublication_year(res.wasNull() ? null : year);

                int pages = res.getInt("pages");
                newBook.setPages(res.wasNull() ? null : pages);

                newBook.setDescription(res.getString("description"));
                newBook.setLanguage(res.getString("language"));

                bookList.add(newBook);
            }
            return bookList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}