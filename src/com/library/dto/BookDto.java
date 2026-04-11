package com.library.dto;

import com.library.models.Book;
import java.util.Scanner;

public class BookDto {
    public static Scanner sc = new Scanner(System.in);

    public int getBookIsbn(){
        int isbn;
        System.out.print("\nEnter the Book ISBN: ");
        isbn = sc.nextInt();

        System.out.println("\nBook Metadata has been accepted");
        return isbn;
    }

    public Book getBookDetails() throws RuntimeException {
        int isbn;
        String title, author, genre;

        System.out.println("\nEnter the Book Metadata:");

        System.out.print("Enter the Book ISBN: ");
        isbn = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the Book Title: ");
        title = sc.nextLine().toLowerCase().trim();

        System.out.print("Enter the Author name: ");
        author = sc.nextLine().toLowerCase().trim();

        System.out.print("Enter the Genre of the Book: ");
        genre = sc.nextLine().toLowerCase().trim();

        System.out.println("\nBook Metadata has been accepted");

        if (isbn <= 0) {
            throw new RuntimeException("ISBN can't be less than or equal to zero");
        }
        if (title.isEmpty()) {
            throw new RuntimeException("Book Title can't be empty");
        }
        if (author.isEmpty()) {
            throw new RuntimeException("Author name can't be Empty!");
        }
        if (genre.isEmpty()) {
            throw new RuntimeException("Genre can't be Empty!");
        }
        if (author.length() < 2 || genre.length() < 2 | title.length() < 2) {
            throw new RuntimeException("Name can't be less than 2 characters");
        }

        // Return the Book Object
        return new Book(
                isbn,
                title,
                author,
                genre
        );
    }
}
