package com.library.controller;

import com.library.dto.BookItemDto;
import com.library.enums.BookCondition;
import com.library.enums.BookStatus;
import com.library.models.BookItem;
import com.library.service.BookItemService;

import java.util.List;
import java.util.Scanner;

public class BookItemController {
    private Scanner sc = new Scanner(System.in);
    private BookItemService service = new BookItemService();

    public void addBookItem() {
        BookItemDto dto = new BookItemDto();
        BookController controller = new BookController();

        System.out.println("Select the ISBN from the given below books:");
        controller.showBookIsbnTitle();

        System.out.print("\nThe ISBN you are looking for doesn't exist?\nEnter yes to add a new Book Metadata else skip: ");
        String user_response = sc.nextLine().trim();

        String isbn;
        if (user_response.equalsIgnoreCase("yes")) {
//
        }

        System.out.print("Enter ISBN: ");
        isbn = sc.nextLine().trim();
        dto.setIsbn(isbn);

        System.out.print("Enter Barcode: ");
        String barcode = sc.nextLine().trim();
        dto.setBarcode(barcode);

        BookStatus status = null;
        while (true) {
            System.out.println("Enter:\n1 -> Available\n2 -> Loaned\n3 -> Reserved\n4 -> Lost\n5 -> Damaged");
            System.out.print("Enter choice (1-5): ");
            try {
                int choice = Integer.parseInt(sc.nextLine());

                status = switch (choice) {
                    case 1 -> BookStatus.AVAILABLE;
                    case 2 -> BookStatus.LOANED;
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
        dto.setBook_status(status);

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
        dto.setBook_condition(condition);

        System.out.print("Enter the Section: ");
        dto.setSection_name(sc.nextLine());

        System.out.print("Enter the Shelf id: ");
        dto.setShelf_id(sc.nextLine());

        try {
            service.addBookItem(dto);

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("Book Item added successfully!");
    }

    public void displayBookCopies() {
        System.out.println("-".repeat(50));

        System.out.print("Do you want to list all removed books? If yes, type yes else skip: ");
        String response = sc.nextLine().trim();

        boolean is_removed = false;
        if (response.equalsIgnoreCase("yes"))
            is_removed = true;

        try {
            List<BookItemDto> books = service.getAllBookCopies(is_removed);

            System.out.println("\nThe Books are: ");
            for (BookItemDto book : books) {
                if (book == null) continue;

                System.out.println("\n");
                System.out.println("Book ISBN: " + book.getIsbn());
                System.out.println("Book Barcode: " + book.getBarcode());
                System.out.println("Shelf Id: " + book.getShelf_id());
                System.out.println("Section: " + book.getSection_name());
                System.out.println("Book Status: " + book.getStatus());
                System.out.println("Book Condition: " + book.getCondition());

            }

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n" + "-".repeat(50));
    }

    public void updateExistingBookItem() {
        System.out.println("-".repeat(50));

        try {
            List<BookItemDto> books = service.getAllBookCopies(false);

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
                BookItemDto exisitngBook = service.getBookItem(barcode);
                BookItemDto updatedBookItem = getUpdatedDetails();

                service.updateBookItem(updatedBookItem, exisitngBook);

                System.out.println("\nBook Item has been updated!");

            } catch (RuntimeException e) {
                System.err.println("ERROR: " + e.getMessage());
            }

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public BookItemDto getUpdatedDetails() {
        System.out.println("\nEnter yes to update the details, else you can skip");
        BookItemDto updatedBookItem = new BookItemDto();
        String choice;

        System.out.print("Enter (yes/no) to update Shelf id: ");
        choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter the new shelf id: ");
            updatedBookItem.setShelf_id(sc.nextLine().trim());
        }

        System.out.print("\nEnter (yes/no) to update section name: ");
        choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter the new section name: ");
            updatedBookItem.setSection_name(sc.nextLine().trim());
        }

        System.out.print("\nEnter (yes/no) to update Book status: ");
        choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter:\n1 -> Available\n2 -> Loaned\n3 -> Reserved\n4 -> Lost\n5 -> Damaged");
            System.out.print("\nEnter choice (1-5): ");

            int bookStatusChoice = Integer.parseInt(sc.nextLine());
            BookStatus status = switch (bookStatusChoice) {
                case 1 -> BookStatus.AVAILABLE;
                case 2 -> BookStatus.LOANED;
                case 3 -> BookStatus.RESERVED;
                case 4 -> BookStatus.LOST;
                case 5 -> BookStatus.DAMAGED;
                default -> null;
            };

            updatedBookItem.setBook_status(status);
        }

        System.out.print("\nEnter (yes/no) to update Book condition: ");
        choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter\n1 -> New\n2 -> Good\n3 -> Worn\n4 -> Damaged");
            System.out.print("Enter choice (1-4): ");

            int bookConditionChoice = Integer.parseInt(sc.nextLine());
            BookCondition condition = switch (bookConditionChoice) {
                case 1 -> BookCondition.NEW;
                case 2 -> BookCondition.GOOD;
                case 3 -> BookCondition.WORN;
                case 4 -> BookCondition.DAMAGED;
                default -> null;
            };

            updatedBookItem.setBook_condition(condition);
        }

        return updatedBookItem;
    }
}
