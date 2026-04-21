package com.library.util;

import com.library.controller.*;
import com.library.dto.UserDto;

public class UserActions {

    private void bookController() {
        System.out.println("=".repeat(20) + " BOOK METADATA SECTION " + "=".repeat(20));
        BookController controller = new BookController();

        String options = """
                \nEnter,
                1 -> To add a book
                2 -> To update a book
                3 -> To delete a book
                4 -> Get all non-deleted books
                5 -> Get all deleted books
                6 -> Get books by author name
                7 -> Get books by genre name
                8 -> Get books by book title
                9 -> Get books summary
                10 -> To view options again
                11 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.addBook();
                case 2 -> controller.updateBook();
                case 3 -> controller.deleteBook();
                case 4 -> controller.getAllBooks();
                case 5 -> controller.getAllDeletedBooks();
                case 6 -> controller.getBooksByAuthor();
                case 7 -> controller.getBooksByGenre();
                case 8 -> controller.getBooksByTitle();
                case 9 -> controller.getBookSummary();
                case 10 -> System.out.println("\n" + options);
                case 11 -> {
                    System.out.println("=".repeat(16) + " EXITING BOOK METADATA SECTION " + "=".repeat(16));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }

    private void bookItemController() {
        System.out.println("=".repeat(22) + " BOOK COPY SECTION " + "=".repeat(22));
        BookItemController controller = new BookItemController();

        String options = """
                Enter,
                1 -> To add a copy
                2 -> To update a copy
                3 -> To delete a copy
                4 -> To get all copies
                5 -> To get copies by author name
                6 -> To get copies by genre name
                7 -> To get copies by section name
                8 -> To get copies by book status
                9 -> To get copies by book condition
                10 -> To view options again
                11 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.addBookItem();
                case 2 -> controller.updateBookItem();
                case 3 -> controller.deleteBookItem();
                case 4 -> controller.getAllCopies();
                case 5 -> controller.getBookByAuthorName();
                case 6 -> controller.getBooksByGenreName();
                case 7 -> controller.getBooksBySectionName();
                case 8 -> controller.getCopiesByStatus();
                case 9 -> controller.getCopiesByCondition();
                case 10 -> System.out.println("\n" + options);
                case 11 -> {
                    System.out.println("=".repeat(18) + " EXITING BOOK COPY SECTION " + "=".repeat(18));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }

    private void borrowBookController() {
        System.out.println("=".repeat(22) + " ISSUE BOOK SECTION " + "=".repeat(22));
        BorrowBookController controller = new BorrowBookController();

        String options = """
                Enter,
                1 -> To issue a book
                2 -> To reject a applicant
                3 -> To collect a book
                4 -> To get all borrowed books
                5 -> To get all returned books
                6 -> To get all non-returned books
                7 -> To get all overdue user
                8 -> To get all fined user
                9 -> To fine all over due users
                10 -> To get all books issued to a user
                11 -> To get all over dues of a user
                12 -> To get all fines of a user
                13 -> To To view options again
                14 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.issueBook();
                case 2 -> controller.rejectApplicant();
                case 3 -> controller.collectBook();
                case 4 -> controller.getAllBorrowedBooks();
                case 5 -> controller.getAllReturnedBooks();
                case 6 -> controller.getAllNonReturnedBooks();
                case 7 -> controller.getAllOverdueUsers();
                case 8 -> controller.getAllFinedUsers();
                case 9 -> controller.fineAllUsers();
                case 10 -> controller.getBooksIssuedToUser();
                case 11 -> controller.getUserOverdue();
                case 12 -> controller.getFinedUser();
                case 13 -> System.out.println("\n" + options);
                case 14 -> {
                    System.out.println("=".repeat(18) + " EXITING ISSUE BOOK SECTION " + "=".repeat(18));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }

    private void userController(UserDto userData) {
        System.out.println("=".repeat(24) + " USER SECTION " + "=".repeat(24));
        UserController controller = new UserController();

        String options = """
                Enter,
                1 -> To get all users
                2 -> To blacklist a user
                3 -> To update user details
                4 -> To get user summary
                5 -> To view options again
                6 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.getAllUsers();
                case 2 -> controller.blacklistUser();
                case 3 -> controller.updateUserDetails(userData);
                case 4 -> controller.getUserSummary();
                case 5 -> System.out.println("\n" + options);
                case 6 -> {
                    System.out.println("=".repeat(20) + " EXITING USER SECTION " + "=".repeat(20));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }


    public UserDto librarianOptionsList(UserDto userData) {
        System.out.println("\n" + "=".repeat(22) + " WELCOME! LIBRARIAN " + "=".repeat(21));

        String options = """
                Enter,
                1 -> To perform operations related to Books (like adding, updating, deleting book metadata, etc...)
                2 -> To perform operations related to Book Copies (Just like Books, but it's on Copies)
                3 -> To perform operations related to issuing books
                4 -> To update user details
                5 -> To Logout""";
        System.out.println(options);

        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> {
                    bookController();
                    System.out.println("\n" + options);
                }
                case 2 -> {
                    bookItemController();
                    System.out.println("\n" + options);
                }
                case 3 -> {
                    borrowBookController();
                    System.out.println("\n" + options);
                }
                case 4 -> {
                    userController(userData);
                    System.out.println("\n" + options);
                }
                case 5 -> {
                    System.out.println("=".repeat(18) + " EXITING LIBRARIAN SECTION " + "=".repeat(18));
                    return null;
                }
                default -> {
                    System.out.println("INVALID CHOICE");
                    System.out.println("\n" + options);
                }
            }
        }
    }

    public UserDto memberOptionsList(UserDto userData) {

        return null;
    }
}
