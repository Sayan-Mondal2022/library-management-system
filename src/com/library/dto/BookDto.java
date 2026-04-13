package com.library.dto;

import com.library.dao.AuthorDao;
import com.library.dao.BookDao;
import com.library.models.Author;
import com.library.models.Book;
import com.library.models.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BookDto {
    private Scanner sc = new Scanner(System.in);

    public Book getBookDetails() throws RuntimeException {
        String isbn, title;
        Book newBook = new Book();
        AuthorDto author_dto = new AuthorDto();
        GenreDto genre_dto = new GenreDto();

        System.out.println("\nEnter the Book Metadata:");

        System.out.print("Enter the Book ISBN: ");
        isbn = sc.nextLine();

        System.out.print("Enter the Book Title: ");
        title = sc.nextLine().toLowerCase().trim();

        int author_id = author_dto.getAuthorId();
        int genre_id = genre_dto.getGenreId();

        newBook.setIsbn(isbn);
        newBook.setTitle(title);
        newBook.setAuthor_id(author_id);
        newBook.setGenre_id(genre_id);

        System.out.print("\nDo you want to add Extra Metadata for this book!\nYou will be adding the following\n1. Publisher Name\n2. Publication year\n3. Number of pages\n4. Description of this book\n5. Book Edition\n6. Book Language\nEnter yes to add extra metadata: ");
        String user_choice = sc.nextLine().trim();
        Map<String, Object> result;

        if (user_choice.equalsIgnoreCase("yes")) {
            result = getExtraMetadata();

            newBook.setPublisher((String) result.get("publisher"));
            newBook.setPublication_year((Integer) result.get("publication_year"));
            newBook.setEdition((String) result.get("edition"));
            newBook.setPages((Integer) result.get("pages"));
            newBook.setDescription((String) result.get("description"));
            newBook.setLanguage((String) result.get("language"));
        }

        System.out.println("\nBook Metadata has been accepted");
        return newBook;
    }

    public Map<String, Object> getExtraMetadata() {

        Map<String, Object> metadata = new HashMap<>();

        System.out.print("\nEnter Publisher Name: ");
        String publisher = sc.nextLine();
        metadata.put("publisher", publisher);

        System.out.print("Enter Publication Year: ");
        int publicationYear;
        while (true) {
            try {
                publicationYear = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input.\nEnter a valid year: ");
            }
        }
        metadata.put("publication_year", publicationYear);

        System.out.print("Enter Number of Pages: ");
        int pages;
        while (true) {
            try {
                pages = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input.\nEnter a valid number: ");
            }
        }
        metadata.put("pages", pages);

        System.out.print("Enter Description: ");
        String description = sc.nextLine();
        metadata.put("description", description);

        System.out.print("Enter Book Edition: ");
        String edition = sc.nextLine();
        metadata.put("edition", edition);

        System.out.print("Enter Book Language: ");
        String language = sc.nextLine();
        metadata.put("language", language);

        return metadata;
    }


    public Book getUpdatedBookDetails() {
        Book updatedBook = new Book();
        AuthorDto author_dto = new AuthorDto();
        GenreDto genre_dto = new GenreDto();


        System.out.println("\nEnter new values (Press ENTER to skip any field)");

        // Title
        System.out.print("Enter new Title: ");
        String title = sc.nextLine().trim();
        if (!title.isEmpty()) {
            updatedBook.setTitle(title.toLowerCase());
        }

        // Author
        System.out.print("Do you want to update Author? (yes/no): ");
        String authorChoice = sc.nextLine().trim();
        if (authorChoice.equalsIgnoreCase("yes")) {
            int author_id = author_dto.getAuthorId();
            updatedBook.setAuthor_id(author_id);
        }

        // Genre
        System.out.print("Do you want to update Genre? (yes/no): ");
        String genreChoice = sc.nextLine().trim();
        if (genreChoice.equalsIgnoreCase("yes")) {
            int genre_id = genre_dto.getGenreId();
            updatedBook.setGenre_id(genre_id);
        }

        // Publisher
        System.out.print("Enter new Publisher: ");
        String publisher = sc.nextLine().trim();
        if (!publisher.isEmpty()) {
            updatedBook.setPublisher(publisher);
        }

        // Publication Year
        System.out.print("Enter new Publication Year: ");
        String yearInput = sc.nextLine().trim();
        if (!yearInput.isEmpty()) {
            try {
                updatedBook.setPublication_year(Integer.parseInt(yearInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid year. Skipping...");
            }
        }

        // Pages
        System.out.print("Enter new Pages: ");
        String pagesInput = sc.nextLine().trim();
        if (!pagesInput.isEmpty()) {
            try {
                updatedBook.setPages(Integer.parseInt(pagesInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid pages. Skipping...");
            }
        }

        // Edition
        System.out.print("Enter new Edition: ");
        String edition = sc.nextLine().trim();
        if (!edition.isEmpty()) {
            updatedBook.setEdition(edition);
        }

        // Language
        System.out.print("Enter new Language: ");
        String language = sc.nextLine().trim();
        if (!language.isEmpty()) {
            updatedBook.setLanguage(language);
        }

        // Description
        System.out.print("Enter new Description: ");
        String description = sc.nextLine().trim();
        if (!description.isEmpty()) {
            updatedBook.setDescription(description);
        }

        System.out.println("\nUpdate input captured.");
        return updatedBook;
    }

    public Book mergeBooks(Book existing, Book updated) {
        Book finalBook = new Book();

        finalBook.setIsbn(existing.getIsbn());

        finalBook.setTitle(
                updated.getTitle() != null ? updated.getTitle() : existing.getTitle()
        );

        finalBook.setAuthor_id(
                updated.getAuthor_id() != 0 ? updated.getAuthor_id() : existing.getAuthor_id()
        );

        finalBook.setGenre_id(
                updated.getGenre_id() != 0 ? updated.getGenre_id() : existing.getGenre_id()
        );

        finalBook.setPublisher(
                updated.getPublisher() != null ? updated.getPublisher() : existing.getPublisher()
        );

        finalBook.setPublication_year(
                updated.getPublication_year() != null ? updated.getPublication_year() : existing.getPublication_year()
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
}
