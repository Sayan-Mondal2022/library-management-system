package com.library.util;

import com.library.controller.BookController;
import com.library.controller.BookItemController;
import com.library.controller.BorrowBookController;
import com.library.controller.UserController;
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

    private void borrowBookController(UserDto userData) {
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
                10 -> To get books issued to a user
                11 -> To get over dues of a user
                12 -> To fine of a user
                13 -> To get all fines of a user
                14 -> To To view options again
                15 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.issueBook();
                case 2 -> controller.rejectApplicant();
                case 3 -> controller.collectBook(userData);
                case 4 -> controller.getAllBorrowedBooks(userData);
                case 5 -> controller.getAllReturnedBooks(userData);
                case 6 -> controller.getAllNonReturnedBooks(userData);
                case 7 -> controller.getAllOverdueUsers();
                case 8 -> controller.getAllFinedUsers();
                case 9 -> controller.fineAllUsers();
                case 10 -> controller.getBooksIssuedToUser();
                case 11 -> controller.getUserOverdue(userData);
                case 12 -> controller.fineUser();
                case 13 -> controller.getFinedUser(userData);
                case 14 -> System.out.println("\n" + options);
                case 15 -> {
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
                case 4 -> controller.getUserSummary(userData);
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
                    borrowBookController(userData);
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


    private void userBookController() {
        System.out.println("=".repeat(20) + " BOOK METADATA SECTION " + "=".repeat(20));
        BookController controller = new BookController();

        String options = """
                \nEnter,
                1 -> Get all books
                2 -> Get books by author name
                3 -> Get books by genre name
                4 -> Get books by book title
                5 -> Get books summary
                6 -> To view options again
                7 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.getAllBooks();
                case 2 -> controller.getBooksByAuthor();
                case 3 -> controller.getBooksByGenre();
                case 4 -> controller.getBooksByTitle();
                case 5 -> controller.getBookSummary();
                case 6 -> System.out.println("\n" + options);
                case 7 -> {
                    System.out.println("=".repeat(16) + " EXITING BOOK METADATA SECTION " + "=".repeat(16));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }


    private void userBookItemController(UserDto userData){
        System.out.println("=".repeat(22) + " BOOK COPY SECTION " + "=".repeat(22));
        BookItemController controller = new BookItemController();

        String options = """
                Enter,
                1 -> To borrow a book
                2 -> To get all copies
                3 -> To get copies by author name
                4 -> To get copies by genre name
                5 -> To get copies by section name
                6 -> To get copies by book status
                7 -> To get copies by book condition
                8 -> To view options again
                9 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.borrowBook(userData);
                case 2 -> controller.getAllCopies();
                case 3 -> controller.getBookByAuthorName();
                case 4 -> controller.getBooksByGenreName();
                case 5 -> controller.getBooksBySectionName();
                case 6 -> controller.getCopiesByStatus();
                case 7 -> controller.getCopiesByCondition();
                case 8 -> System.out.println("\n" + options);
                case 9 -> {
                    System.out.println("=".repeat(18) + " EXITING BOOK COPY SECTION " + "=".repeat(18));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }

    private void userBorrowBookController(UserDto userData){
        System.out.println("=".repeat(22) + " ISSUE BOOK SECTION " + "=".repeat(22));
        BorrowBookController controller = new BorrowBookController();

        String options = """
                Enter,
                1 -> To return a book
                2 -> To get all borrowed books
                3 -> To get all returned books
                4 -> To get all non-returned books
                5 -> To get all over dues
                6 -> To get all fines
                7 -> To To view options again
                8 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.collectBook(userData);
                case 2 -> controller.getAllBorrowedBooks(userData);
                case 3 -> controller.getAllReturnedBooks(userData);
                case 4 -> controller.getAllNonReturnedBooks(userData);
                case 5 -> controller.getUserOverdue(userData);
                case 6 -> controller.getFinedUser(userData);
                case 7 -> System.out.println("\n" + options);
                case 8 -> {
                    System.out.println("=".repeat(18) + " EXITING ISSUE BOOK SECTION " + "=".repeat(18));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }

    private void aboutUser(UserDto userData){
        System.out.println("=".repeat(24) + " USER SECTION " + "=".repeat(24));
        UserController controller = new UserController();

        String options = """
                Enter,
                1 -> To update user details
                2 -> To get user summary
                3 -> To view options again
                4 -> To exit""";

        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> controller.updateUserDetails(userData);
                case 2 -> controller.getUserSummary(userData);
                case 3 -> System.out.println("\n" + options);
                case 4 -> {
                    System.out.println("=".repeat(20) + " EXITING USER SECTION " + "=".repeat(20));
                    return;
                }
                default -> System.out.println("INVALID CHOICE");
            }
        }
    }

    public UserDto memberOptionsList(UserDto userData) {
        System.out.println("=".repeat(25) + " WELCOME USER " + "=".repeat(25));

        String options = """
                Enter,
                1 -> To perform operations related to Books (like reading books)
                2 -> To perform operations related to Book Copies (Like borrowing a book)
                3 -> To perform operations related to Checking fines and returning a book
                4 -> To update user details
                5 -> To Logout""";


        System.out.println(options);
        while (true) {
            int choice = Validators.getValidInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> {
                    userBookController();
                    System.out.println("\n" + options);
                }
                case 2 -> {
                    userBookItemController(userData);
                    System.out.println("\n" + options);
                }
                case 3 -> {
                    userBorrowBookController(userData);
                    System.out.println("\n" + options);
                }
                case 4 -> {
                    aboutUser(userData);
                    System.out.println("\n" + options);
                }
                case 5 -> {
                    System.out.println("=".repeat(21) + " EXITING USER SECTION " + "=".repeat(22));
                    return null;
                }
                default -> {
                    System.out.println("INVALID CHOICE");
                    System.out.println("\n" + options);
                }
            }
        }
    }
}
