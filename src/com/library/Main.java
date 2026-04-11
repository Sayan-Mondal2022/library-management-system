package com.library;

import com.library.dto.BookDto;
import com.library.dto.LoginRequest;
import com.library.models.Book;
import com.library.dao.BookDao;
import com.library.dto.RegistrationRequest;
import com.library.models.User;
import com.library.service.LoginService;
import com.library.service.RegistrationService;
import com.library.util.ConsoleView;
import com.library.util.InputHandler;
import com.library.util.MenuController;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static User userLogin() {
        try {
            LoginRequest request;

            while (true) {
                try {
                    System.out.println("\n" + "=".repeat(50));
                    request = InputHandler.getLoginDetails();
                    break;
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Please try again...\n");
                }
            }

            LoginService login_service = new LoginService();    // If it throws a validation then catch will handle it.

            return login_service.loginUser(request);

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return null;
    }

    public static User user_data = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        while (user_data == null) {
            System.out.print("\nExisting user?\tEnter 1 to Login\nNew user?\tEnter 2 to Register\nWant to exit?\tEnter 3 to close!\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice < 1 || choice > 3) {
                System.out.println("\nInvalid entry! Please try again\n");
                continue;
            }

            if (choice == 1) {
                user_data = userLogin();

                if (user_data != null) {
                    System.out.println("User Name is: " + user_data.getUserName());
                    System.out.println("Login Successful!");
                }
                System.out.println("\n" + "=".repeat(50));

            } else if (choice == 2) {
                try {
                    // InputHandler will be a helper function which will get the userInputs for registration.
                    RegistrationRequest request;

                    while (true) {
                        try {
                            System.out.println("\n" + "=".repeat(50));
                            request = InputHandler.getUserInput();
                            break;
                        } catch (RuntimeException e) {
                            System.out.println("Error: " + e.getMessage());
                            System.out.println("Please try again...\n");
                        }
                    }

                    RegistrationService registrationService = new RegistrationService();

                    registrationService.registerUser(request);
                    System.out.println("User Registration Successful!!");
                    System.out.println("\n" + "=".repeat(50));

                    // User Login
                    user_data = userLogin();

                    if (user_data != null) {
                        System.out.println("User Name is: " + user_data.getUserName());
                        System.out.println("Login Successful!");
                    }
                    System.out.println("\n" + "=".repeat(50));

                } catch (RuntimeException e) {
                    System.err.println("Error" + e.getMessage());
                }
            } else {
                System.out.println("\nExiting the Library Management system...");
                break;
            }


            if (user_data != null) {
                System.out.println("\n\n" + "=".repeat(50));

                System.out.println("\nWELCOME TO LIBRARY MANAGEMENT SYSTEM!!");

                System.out.println("\nGreetings! " + user_data.getUserName());
                System.out.println("You are a '" + user_data.getUserType() + "' in this Library.");

                System.out.println("\n" + "=".repeat(50));

                if (user_data.getUserType().equalsIgnoreCase("librarian")) {
                    while (user_data != null) {
                        System.out.println("\nEnter 101\t-> To list all actions that a Librarian can perform");
                        // These will be the Operations that is Accessible only to the Librarian.
                        System.out.println("Enter 01\t-> To Show ALl books");
                        System.out.println("Enter 02\t-> To Show Only Deleted books");

                        System.out.println("Enter 03\t-> Add a New Book");
                        System.out.println("Enter 04\t-> Update a Book");
                        System.out.println("Enter 05\t-> Delete a Book");

                        // The Options will be changed at last.
                        System.out.println("Enter 09\t-> To search a book by ISBN");
                        System.out.println("Enter 10\t-> To search a book by Author");
                        System.out.println("Enter 11\t-> To search a book by Title");
                        System.out.println("Enter 12\t-> To search a book by Genre");

                        System.out.println("Enter 06\t-> To show all Users");
                        System.out.println("Enter 07\t-> To show all Members");
                        System.out.println("Enter 08\t-> To show all Librarians");
                        System.out.println("Enter \t-> Add a BookItem for a Existing book");
                        System.out.println("Enter \t-> Update a BookItem for a Existing book");
                        System.out.println("Enter 100\t-> To logout");


                        System.out.print("\nPlease enter your choice: ");
                        int user_choice = sc.nextInt();
                        sc.nextLine();

                        if (user_choice == 1) {
                            // List all the books (Which are not deleted)
                            ConsoleView.displayBooks("allbooks");

                        } else if (user_choice == 2) {
                            // List only the Deleted Books
                            ConsoleView.displayBooks("deleted");

                        } else if (user_choice == 3) {
                            // This block will be used to add a book into my DB
                            MenuController.insertBook();

                        } else if (user_choice == 4) {
                            // This block will be to update all the books.
                            MenuController.updateBook();

                        } else if (user_choice == 5) {
                            // This will be meant to delete the user.
                            MenuController.deleteBook();

                        } else if (user_choice == 6) {
                            // Show all users:
                            ConsoleView.displayUsers("allUsers");

                        } else if (user_choice == 7) {
                            // Show only Members
                            ConsoleView.displayUsers("member");

                        } else if (user_choice == 8) {
                            // Show only Librarian
                            ConsoleView.displayUsers("librarian");

                        } else if (user_choice == 9) {
                            // ISBN
                            try {
                                String query = MenuController.getSearchQuery("isbn");
                                ConsoleView.displaySearchedBooks("isbn", query);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 10) {
                            // author
                            try {
                                String query = MenuController.getSearchQuery("author");
                                ConsoleView.displaySearchedBooks("author", query);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 11) {
                            // Title
                            try {
                                String query = MenuController.getSearchQuery("title");
                                ConsoleView.displaySearchedBooks("title", query);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 12) {
                            // Genre
                            try {
                                String query = MenuController.getSearchQuery("genre");
                                ConsoleView.displaySearchedBooks("genre", query);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }


                        } else if (user_choice == 100) {
                            // Logout the User
                            user_data = null;
                            System.out.println("Logging out...");

                        } else {
                            System.out.println("ERROR: INVALID USER CHOICE\nPlease try again\n");
                        }
                    }
                } else {
                    while (user_data != null) {
                        System.out.println("Enter 102\t-> To list all actions that a Member can perform");
                        // These will be accessible only to user.
                        System.out.println("Enter 1\t-> To Show ALl books");

                        System.out.print("\nPlease enter your choice: ");
                        int user_choice = sc.nextInt();
                        sc.nextLine();

                        if (user_choice == 1) {
                            ConsoleView.displayBooks("allbooks");
                        }


                    }
                }
            }

            System.out.println("\n");
        }
    }
}