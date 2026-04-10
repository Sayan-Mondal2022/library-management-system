package com.library.util;

import com.library.dao.BookDao;
import com.library.dto.BookDto;
import com.library.models.Book;

public class MenuController {
    public static BookDto book_dto = new BookDto();
    public static BookDao book_dao = new BookDao();

    // This function will be used to Insert the books
    public static void insertBook(){
        System.out.println("\n" + "-".repeat(50));
        Book book_data = book_dto.getBookDetails();
        try {
            book_dao.librarianAddBook(book_data);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "-".repeat(50));
    }

    // This function will be used to update the Book
    public static void updateBook(){
        System.out.println("\n" + "-".repeat(50));
        Book book_data = book_dto.getBookDetails();
        try {
            book_dao.librarianUpdateBook(book_data);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "-".repeat(50));
    }

    // This function will be used to Delete a Book
    public static void deleteBook(){
        System.out.println("\n" + "-".repeat(50));

        // Get the ISBN from the user.
        int isbn = book_dto.getBookIsbn();
        try {
            book_dao.librarianRemoveBook(isbn);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n" + "-".repeat(50));
    }
}
