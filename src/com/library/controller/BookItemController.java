package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.BookItemDto;
import com.library.dto.UserDto;
import com.library.enums.BookCondition;
import com.library.enums.BookStatus;
import com.library.service.BookItemService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BookItemController {
    private final Scanner sc = new Scanner(System.in);
    private final BookItemService service = new BookItemService();


    private BookStatus getBookStatus() {
        BookStatus status = null;
        while (true) {
            System.out.println("Enter:\n1 -> Available\n2 -> Loaned\n3 -> Reserved\n4 -> Lost\n5 -> Damaged");
            System.out.print("Enter choice (1-5): ");
            try {
                int choice = Integer.parseInt(sc.nextLine());

                status = switch (choice) {
                    case 1 -> BookStatus.AVAILABLE;
                    case 2 -> BookStatus.ISSUED;
                    case 3 -> BookStatus.RESERVED;
                    case 4 -> BookStatus.LOST;
                    case 5 -> BookStatus.DAMAGED;
                    default -> null;
                };

                if (status != null) break;
                else System.out.println("Invalid choice!");

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }
        return status;
    }

    private BookCondition getBookCondition() {
        BookCondition condition = null;
        while (true) {
            System.out.println("Enter\n1 -> New\n2 -> Good\n3 -> Worn\n4 -> Damaged");
            System.out.print("Enter choice (1-4): ");
            try {
                int choice = Integer.parseInt(sc.nextLine());

                condition = switch (choice) {
                    case 1 -> BookCondition.NEW;
                    case 2 -> BookCondition.GOOD;
                    case 3 -> BookCondition.WORN;
                    case 4 -> BookCondition.DAMAGED;
                    default -> null;
                };

                if (condition != null) break;
                else System.out.println("Invalid choice!");

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }

        return condition;
    }


    private void displayBookIsbnTitle(List<BookDto> responseList) throws RuntimeException {
        if (responseList.isEmpty())
            throw new RuntimeException("NO BOOKS FOUND");

        for (BookDto book : responseList) {
            System.out.println("\nBook ISBN: " + book.getIsbn());
            System.out.println("Book Title: " + book.getTitle());
        }
    }


    private void displayCopies(List<BookItemDto> books) throws RuntimeException {
        if (books.isEmpty())
            throw new RuntimeException("BOOKs NOT FOUND");

        System.out.println("\nBOOKs ARE: ");

        for (BookItemDto book : books) {
            if (book == null) continue;

            System.out.println("\nBook ISBN: " + book.getIsbn());
            System.out.println("Book Barcode: " + book.getBarcode());
            System.out.println("Book Title: " + book.getTitle());
            System.out.println("Author name: " + book.getAuthorName());
            System.out.println("Genre name: " + book.getGenreName());
            System.out.println("Shelf Id: " + book.getShelfId());
            System.out.println("Section: " + book.getSectionName());
            System.out.println("Book Status: " + book.getStatus());
            System.out.println("Book Condition: " + book.getCondition());

        }
    }

    private void getBookItemDetails() throws SQLException {
        System.out.println("Select the ISBN from the given below books:");
        try {
            List<BookDto> responseList = service.getIsbnTitle();
            displayBookIsbnTitle(responseList);

            BookItemDto dto = new BookItemDto();
            System.out.print("\nEnter ISBN: ");
            String isbn = sc.nextLine().trim();
            dto.setIsbn(isbn);

            System.out.print("Enter Barcode: ");
            String barcode = sc.nextLine().trim();
            dto.setBarcode(barcode);

            BookStatus status = getBookStatus();
            dto.setBookStatus(status);

            BookCondition condition = getBookCondition();
            dto.setBookCondition(condition);

            System.out.print("Enter the Section: ");
            dto.setSectionName(sc.nextLine());

            System.out.print("Enter the Shelf id: ");
            dto.setShelfId(sc.nextLine());

            service.addBookItem(dto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        System.out.println("BOOK ITEM ADDED SUCCESSFULLY!");
    }


    public void addBookItem() {
        System.out.println("-".repeat(50));

        try {
            System.out.print("""
                    Enter 1 -> If Book Metadata already exist
                    Enter 2 -> If Book Metadata doesn't exist
                    Enter your choice:\s""");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                getBookItemDetails();
            } else if (choice == 2) {
                new BookController().addBook();
                System.out.println("\n");
                getBookItemDetails();
            } else
                System.out.println("INVALID INPUT");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void updateBookItem() {
        System.out.println("-".repeat(50));

        try {
            List<BookItemDto> books = service.getAllCopies(false);

            System.out.println("The Books are: ");
            for (BookItemDto book : books) {
                if (book == null) continue;

                System.out.println("\n");
                System.out.println("Book ISBN: " + book.getIsbn());
                System.out.println("Book Barcode: " + book.getBarcode());
            }

            System.out.println("\n");
            System.out.print("Enter the Book Barcode: ");
            String barcode = sc.nextLine().trim();

            try {
                BookItemDto existingBook = service.getBookItem(barcode);
                BookItemDto updatedBookItem = getUpdatedDetails();

                service.updateBookItem(updatedBookItem, existingBook);

                System.out.println("\nBook Item has been updated!");

            } catch (RuntimeException e) {
                System.err.println("ERROR: " + e.getMessage());
            }

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }

    private BookItemDto getUpdatedDetails() {
        System.out.println("\nEnter yes to update the details, else you can skip");
        BookItemDto updatedBookItem = new BookItemDto();

        System.out.print("Enter (yes/no) to update Shelf id: ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter the new shelf id: ");
            updatedBookItem.setShelfId(sc.nextLine().trim());
        }

        System.out.print("\nEnter (yes/no) to update section name: ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter the new section name: ");
            updatedBookItem.setSectionName(sc.nextLine().trim());
        }

        System.out.print("\nEnter (yes/no) to update Book status: ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            updatedBookItem.setBookStatus(getBookStatus());
        }

        System.out.print("\nEnter (yes/no) to update Book condition: ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            updatedBookItem.setBookCondition(getBookCondition());
        }

        return updatedBookItem;
    }



    public void deleteBookItem() {
        System.out.println("-".repeat(50));

        try {
            List<BookItemDto> books = service.getAllCopies(false);
            displayCopies(books);

            System.out.print("\nEnter the Book Barcode you want to delete: ");
            String barcode = sc.nextLine();

            service.deleteBookItem(barcode);
            System.out.println("BOOK ITEM HAS BEEN DELETED");

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }



    public void getAllCopies() {
        System.out.println("-".repeat(50));

        try {
            System.out.print("Do you want to list all removed books? If yes, type yes else skip: ");

            List<BookItemDto> books;
            if (sc.nextLine().equalsIgnoreCase("yes"))
                books = service.getAllCopies(true);
            else
                books = service.getAllCopies(false);
            displayCopies(books);

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n" + "-".repeat(50));
    }



    private void getAttributeResponse(String queryType) {
        System.out.println("\n" + "-".repeat(50));

        try {
            List<BookItemDto> books;

            if (queryType.equalsIgnoreCase("status")) {
                String bookStatus = getBookStatus().toString();
                books = service.getCopiesByStatus(bookStatus);
            } else {
                String bookCondition = getBookCondition().toString();
                books = service.getCopiesByCondition(bookCondition);
            }

            displayCopies(books);

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n" + "-".repeat(50));
    }

    public void getCopiesByStatus() {
        getAttributeResponse("status");
    }

    public void getCopiesByCondition() {
        getAttributeResponse("condition");
    }



    private void displayNames(List<String> names) throws RuntimeException {
        if (names.isEmpty())
            throw new RuntimeException("NAMES LIST IS EMPTY");

        System.out.println();
        for (String name : names) {
            System.out.println("Name: " + name);
        }
    }

    private String getDetailsOnQuery(String queryType) throws SQLException, RuntimeException {
        try {
            List<String> names;
            if (queryType.equalsIgnoreCase("author"))
                names = service.getAllAuthors();
            else if (queryType.equalsIgnoreCase("genre"))
                names = service.getAllGenres();
            else
                names = service.getAllSections();
            displayNames(names);


            System.out.print("Enter the name: ");
            return sc.nextLine();

        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    private void getNameResponse(String queryType) {
        System.out.println("\n" + "-".repeat(50));

        try {
            String name = getDetailsOnQuery(queryType);
            String status = getBookStatus().toString();

            List<BookItemDto> books;
            if (queryType.equalsIgnoreCase("author"))
                books = service.getCopiesByAuthorName(name, status);
            else if (queryType.equalsIgnoreCase("genre"))
                books = service.getCopiesByGenreName(name, status);
            else
                books = service.getCopiesBySectionName(name, status);

            displayCopies(books);

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n" + "-".repeat(50));
    }

    public void getBookByAuthorName() {
        getNameResponse("author");
    }

    public void getBooksByGenreName() {
        getNameResponse("genre");
    }

    public void getBooksBySectionName() {
        getNameResponse("section");
    }


    public void borrowBook(UserDto userData) {
        System.out.println("-".repeat(60));

        try {
            List<BookItemDto> books = service.getCopiesByStatus(BookStatus.AVAILABLE.toString());

            System.out.println("\n" + "-".repeat(18) + " DISPLAYING BOOKS " + "-".repeat(18));
            displayCopies(books);
            System.out.println("\n" + "-".repeat(18) + " END OF BOOKS LIST " + "-".repeat(18));

            System.out.print("\nEnter the barcode: ");
            String barcode = sc.nextLine();

            for (BookItemDto book : books){
                if (book.getBarcode().equalsIgnoreCase(barcode.toLowerCase())){
                    service.borrowBook(userData.getUserId(), barcode);
                    System.out.println("Book has been applied, to get the approval");
                    return;
                }
            }

            System.out.println("Invalid barcode!");


        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(60));
    }
}
