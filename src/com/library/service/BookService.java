package com.library.service;

import com.library.dao.BookDao;
import com.library.dto.BookDto;
import com.library.models.Book;

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

    public void addBook(String insertType, BookDto dto) throws RuntimeException {
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

        try {
            if (insertType.equalsIgnoreCase("full"))
                dao.librarianAddBook("full", book);
            else
                dao.librarianAddBook("partial", book);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(BookDto existingBook, BookDto updatedBook) throws RuntimeException{
        Book finalBook = mergeBooks(existingBook, updatedBook);
        dao.librarianUpdateBook(finalBook);
    }

    public List<BookDto> getAllBooks(boolean isDeleted) throws RuntimeException{
        return dao.getAllBooks(isDeleted);
    }

    public List<BookDto> getBookIsbnTitle(boolean isDeleted) throws RuntimeException{
        return dao.getBookIsbnTitle(isDeleted);
    }

    public BookDto getBookByIsbn(String isbn) throws RuntimeException{
        return dao.getBookByIsbn(isbn);
    }

    public void deleteBook(String isbn) throws RuntimeException{
        dao.librarianDeleteBook(isbn);
    }

    public List<BookDto> getBooksByAuthor(int authorId) throws RuntimeException{
        return dao.getBooksByAuthor(authorId);
    }

    public List<BookDto> getBooksByGenre(int genreId) throws RuntimeException{
        return dao.getBooksByGenre(genreId);
    }


    public List<BookDto> getBooksByTitle(String title) throws RuntimeException{
        return dao.getBooksByTitle(title);
    }

}
