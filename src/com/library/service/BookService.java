package com.library.service;

import com.library.dao.BookDao;
import com.library.dto.BookDto;
import com.library.dto.BookSummaryDto;
import com.library.models.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDao dao = new BookDao();

    private Book mergeBooks(BookDto existing, BookDto updated) {
        Book finalBook = new Book();
        finalBook.setIsbn(existing.getIsbn());

        finalBook.setTitle(
                updated.getTitle() != null ? updated.getTitle() : existing.getTitle()
        );

        finalBook.setAuthorId(
                updated.getAuthorId() != 0 ? updated.getAuthorId() : existing.getAuthorId()
        );

        finalBook.setGenreId(
                updated.getGenreId() != 0 ? updated.getGenreId() : existing.getGenreId()
        );

        finalBook.setPublisher(
                updated.getPublisher() != null ? updated.getPublisher() : existing.getPublisher()
        );

        finalBook.setPublicationYear(
                updated.getPublicationYear() != null ? updated.getPublicationYear() : existing.getPublicationYear()
        );

        finalBook.setPages(
                updated.getPages() != null ? updated.getPages() : existing.getPages()
        );

        finalBook.setEdition(
                updated.getEdition() != null ? updated.getEdition() : existing.getEdition()
        );

        finalBook.setLanguage(
                updated.getLanguage() != null ? updated.getLanguage() : existing.getLanguage()
        );

        finalBook.setDescription(
                updated.getDescription() != null ? updated.getDescription() : existing.getDescription()
        );

        return finalBook;
    }

    public void addBook(String insertType, BookDto dto) throws SQLException {
        Book book = new Book();

        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setAuthorId(dto.getAuthorId());
        book.setGenreId(dto.getGenreId());

        book.setPublisher(dto.getPublisher());
        book.setPublicationYear(dto.getPublicationYear());
        book.setPages(dto.getPages());
        book.setEdition(dto.getEdition());
        book.setLanguage(dto.getLanguage());
        book.setDescription(dto.getDescription());


        if (insertType.equalsIgnoreCase("full"))
            dao.librarianAddBook("full", book);
        else
            dao.librarianAddBook("partial", book);
    }

    public void updateBook(BookDto existingBook, BookDto updatedBook) throws SQLException {
        Book finalBook = mergeBooks(existingBook, updatedBook);
        dao.librarianUpdateBook(finalBook);
    }

    public List<BookDto> getAllBooks(boolean isDeleted) throws SQLException {
        return dao.getAllBooks(isDeleted);
    }

    public List<BookDto> getBookIsbnTitle(boolean isDeleted) throws SQLException {
        return dao.getBookIsbnTitle(isDeleted);
    }

    public BookDto getBookByIsbn(String isbn) throws SQLException {
        return dao.getBookByIsbn(isbn);
    }

    public void deleteBook(String isbn) throws SQLException {
        dao.librarianDeleteBook(isbn);
    }

    public List<BookDto> getBooksByAuthor(int authorId) throws SQLException {
        return dao.getBooksByAuthor(authorId);
    }

    public List<BookDto> getBooksByGenre(int genreId) throws SQLException {
        return dao.getBooksByGenre(genreId);
    }


    public List<BookDto> getBooksByTitle(String title) throws SQLException {
        return dao.getBooksByTitle(title);
    }


    public BookSummaryDto getBookSummaryByIsbn(String isbn) throws SQLException {
        return dao.getBookSummaryByIsbn(isbn);
    }

}
