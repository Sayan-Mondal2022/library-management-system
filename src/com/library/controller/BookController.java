package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.BookSummaryDto;
import com.library.service.BookService;
import com.library.util.Validators;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BookController {
    private final Scanner sc = new Scanner(System.in);
    private final BookService service = new BookService();
    private final AuthorController authorController = new AuthorController();
    private final GenreController genreController = new GenreController();

    public void addBook() {
        System.out.println("-".repeat(20) + " ADDING BOOK METADATA " + "-".repeat(20));

        try {
            System.out.println("\nEnter Book Details:");

            BookDto dto = new BookDto();

            System.out.print("ISBN: ");
            dto.setIsbn(sc.nextLine());

            System.out.print("Title: ");
            dto.setTitle(sc.nextLine().trim().toLowerCase());

            dto.setAuthorId(authorController.selectAuthor());
            dto.setGenreId(genreController.selectGenre());

            System.out.print("Add extra metadata? (yes/no): ");
            String addMetadata = sc.nextLine();
            if (addMetadata.equalsIgnoreCase("yes")) {
                fillExtraMetadata(dto);
            }

            if (addMetadata.equalsIgnoreCase("yes"))
                service.addBook("full", dto);
            else
                service.addBook("partial", dto);
            System.out.println("Book Added!");

        } catch (SQLException | RuntimeException e) {
            System.out.print("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(26) + " EXITING " + "-".repeat(28));
    }

    private void fillExtraMetadata(BookDto dto) {
        System.out.println("\n" + "-".repeat(15) + " ADDING EXTRA METADATA " + "-".repeat(15));
        System.out.print("Publisher: ");
        dto.setPublisher(sc.nextLine());

        dto.setPublicationYear(Validators.getValidInt("Publication Year: "));
        dto.setPages(Validators.getValidInt("Pages: "));

        System.out.print("Edition: ");
        dto.setEdition(sc.nextLine());

        System.out.print("Language: ");
        dto.setLanguage(sc.nextLine());

        System.out.print("Description: ");
        dto.setDescription(sc.nextLine());
    }

    private void displayMetadata(List<BookDto> books, String displayType) {
        if (books.isEmpty())
            throw new RuntimeException("BOOKS NOT FOUND");

        System.out.println("TOTAL BOOKS RETRIEVED ARE: " + books.size());

        for (BookDto book : books) {
            System.out.println("\nBook ISBN: " + book.getIsbn());
            System.out.println("Book Title: " + book.getTitle());

            if (displayType.equalsIgnoreCase("full")) {
                System.out.println("Author Name: " + book.getAuthorName());
                System.out.println("Genre Name: " + book.getGenreName());

                System.out.println("Publisher: " + (book.getPublisher() != null ? book.getPublisher() : "N/A"));
                System.out.println("Publication year: " + (book.getPublicationYear() != null ? book.getPublicationYear() : "N/A"));
                System.out.println("Book Edition: " + (book.getEdition() != null ? book.getEdition() : "N/A"));
                System.out.println("Description: " + (book.getDescription() != null ? book.getDescription() : "N/A"));
                System.out.println("Language: " + (book.getLanguage() != null ? book.getLanguage() : "N/A"));
                System.out.println("Pages: " + (book.getPages() != null ? book.getPages() : "N/A"));
            }
        }
    }

    public void getAllBooks() {
        System.out.println("-".repeat(22) + " DISPLAYING BOOKS " + "-".repeat(22));

        try {
            List<BookDto> books = service.getAllBooks(false);
            displayMetadata(books, "full");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }

    public void getAllDeletedBooks() {
        System.out.println("-".repeat(22) + " DISPLAYING BOOKS " + "-".repeat(22));

        try {
            List<BookDto> books = service.getAllBooks(true);
            displayMetadata(books, "full");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }


    public void updateBook() {
        System.out.println("-".repeat(25) + " UPDATE BOOK " + "-".repeat(25));

        try {
            List<BookDto> books = service.getBookIsbnTitle(false);
            System.out.println("BOOKs ARE:");
            displayMetadata(books, "partial");

            System.out.print("\nEnter the ISBN: ");
            String isbn = sc.nextLine();

            BookDto existingBook = service.getBookByIsbn(isbn);
            System.out.println("\nEXISTING BOOK IS:");
            displayMetadata(List.of(existingBook), "full");

            BookDto updatedBook = new BookDto();
            updatedBook.setIsbn(existingBook.getIsbn());

            System.out.println("\nEnter 'yes' to change the details else skip");
            System.out.print("Update Book Title? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                System.out.print("Enter title: ");
                updatedBook.setTitle(sc.nextLine());
                System.out.println();
            }

            System.out.print("Update Author Name? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                updatedBook.setAuthorId(authorController.selectAuthor());
                System.out.println();
            }

            System.out.print("Update Genre Name? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                updatedBook.setGenreId(genreController.selectGenre());
                System.out.println();
            }


            System.out.print("Update Publisher? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                System.out.print("Enter Publisher: ");
                updatedBook.setPublisher(sc.nextLine());
                System.out.println();
            }

            System.out.print("Update Publication year? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                updatedBook.setPublicationYear(Validators.getValidInt("Enter Publication year: "));
                System.out.println();
            }

            System.out.print("Update Book Pages? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                updatedBook.setPages(Validators.getValidInt("Enter Book Pages: "));
                System.out.println();
            }

            System.out.print("Update Book Edition? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                System.out.print("Book Edition: ");
                updatedBook.setEdition(sc.nextLine());
                System.out.println();
            }

            System.out.print("Update Language? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                System.out.print("Book Language: ");
                updatedBook.setLanguage(sc.nextLine());
                System.out.println();
            }

            System.out.print("Update Book Description? ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                System.out.print("Book Description: ");
                updatedBook.setDescription(sc.nextLine());
                System.out.println();
            }

            service.updateBook(existingBook, updatedBook);
            System.out.println("\nBOOK DETAILS HAVE BEEN UPDATED");

        } catch (SQLException | RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }

    public void deleteBook() {
        System.out.println("-".repeat(25) + " DELETE BOOK " + "-".repeat(25));

        try {
            List<BookDto> books = service.getBookIsbnTitle(false);
            displayMetadata(books, "partial");

            System.out.print("\nEnter the ISBN: ");
            String isbn = sc.nextLine();

            service.deleteBook(isbn);
            System.out.println("BOOK HAS BEEN DELETED");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }


    public void getBooksByAuthor() {
        System.out.println("-".repeat(22) + " DISPLAYING BOOKS " + "-".repeat(22));

        try {
            authorController.displayAuthors();
            int authorId = Validators.getValidInt("Enter Author Id: ");
            List<BookDto> books = service.getBooksByAuthor(authorId);

            displayMetadata(books, "full");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }


    public void getBooksByGenre() {
        System.out.println("-".repeat(22) + " DISPLAYING BOOKS " + "-".repeat(22));

        try {
            genreController.displayGenres();
            int genreId = Validators.getValidInt("Enter genreId: ");
            List<BookDto> books = service.getBooksByGenre(genreId);

            displayMetadata(books, "full");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }


    public void getBooksByTitle() {
        System.out.println("-".repeat(22) + " DISPLAYING BOOKS " + "-".repeat(22));

        try {
            List<BookDto> books = service.getBookIsbnTitle(false);
            displayMetadata(books, "partial");

            System.out.print("\nEnter the Book Title: ");
            String title = sc.nextLine();

            books = service.getBooksByTitle(title);
            displayMetadata(books, "full");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }


    private void displayBookSummary(String isbn) {
        try {
            BookSummaryDto dto = service.getBookSummaryByIsbn(isbn);

            if (dto == null) {
                System.out.println("BOOK COPIES NOT FOUND");
                return;
            }

            System.out.println("\n" + "=".repeat(17) + " BOOK SUMMARY " + "=".repeat(17));
            System.out.println("\nISBN: " + dto.getIsbn());
            System.out.println("Title: " + dto.getTitle());
            System.out.println("Author: " + dto.getAuthorName());
            System.out.println("Genre: " + dto.getGenreName());

            System.out.println("Publisher: " + dto.getPublisher());
            System.out.println("Year: " + dto.getPublicationYear());
            System.out.println("Pages: " + dto.getPages());
            System.out.println("Edition: " + dto.getEdition());
            System.out.println("Language: " + dto.getLanguage());

            System.out.println("Sections: " + dto.getSectionNames());

            System.out.println("Total Copies: " + dto.getTotalCopies());
            System.out.println("Issued Copies: " + dto.getTotalCopiesIssued());
            System.out.println("Available Copies: " + dto.getTotalAvailableCopies());
            System.out.println("\n" + "=".repeat(14) + " END OF BOOK SUMMARY " + "=".repeat(14));

        } catch (SQLException | RuntimeException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    public void getBookSummary() {
        System.out.println("-".repeat(25) + " GET BOOK SUMMARY " + "-".repeat(25));
        try {
            List<BookDto> books = service.getAllBooks(false);

            System.out.println("\n" + "-".repeat(18) + " DISPLAYING BOOKS " + "-".repeat(18));
            displayMetadata(books, "partial");
            System.out.println("\n" + "-".repeat(18) + " END OF BOOKS LIST " + "-".repeat(18));

            System.out.println("\n");
            System.out.print("Enter the Book ISBN: ");
            String isbn = sc.nextLine();

            displayBookSummary(isbn);

        } catch (SQLException | RuntimeException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
        System.out.println("-".repeat(28) + " EXITING " + "-".repeat(28));
    }

}