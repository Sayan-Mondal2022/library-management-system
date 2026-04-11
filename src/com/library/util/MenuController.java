package com.library.util;

import com.library.dao.BookDao;
import com.library.dto.BookDto;
import com.library.models.Book;

import java.util.Scanner;

public class MenuController {
    public static BookDto book_dto = new BookDto();
    public static BookDao book_dao = new BookDao();
    public static Scanner sc = new Scanner(System.in);

    // This function will be used to Insert the books
    public static void insertBook() {
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
    public static void updateBook() {
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
    public static void deleteBook() {
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

    public static String getSearchQuery(String query_type) throws RuntimeException{
        System.out.println("\n" + "-".repeat(50));

        String query;
        if (query_type.equalsIgnoreCase("isbn")) {
            System.out.print("Enter the Book ISBN: ");
            query = sc.nextLine().trim();
        } else if (query_type.equalsIgnoreCase("author")) {
            System.out.print("Enter the Author name: ");
            query = sc.nextLine().trim();
        } else if (query_type.equalsIgnoreCase("genre")) {
            System.out.print("Enter the genre: ");
            query = sc.nextLine().trim();
        } else {
            System.out.print("Enter the Book title: ");
            query = sc.nextLine().trim();
        }

        if (query.isEmpty())
            throw new RuntimeException("Inout query can't be empty");

        return query;
    }
}
