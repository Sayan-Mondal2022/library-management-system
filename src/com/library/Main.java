package com.library;

import com.library.dto.BookDto;
import com.library.dto.LoginRequest;
import com.library.models.Book;
import com.library.dao.BookDao;
import com.library.dto.RegistrationRequest;
import com.library.models.User;
import com.library.service.LoginService;
import com.library.service.RegistrationService;
import com.library.util.InputHandler;

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
                System.out.println("\n\n"+ "=".repeat(50));

                System.out.println("\nWELCOME TO LIBRARY MANAGEMENT SYSTEM!!");

                System.out.println("\nGreetings! " + user_data.getUserName());
                System.out.println("You are a '" + user_data.getUserType() + "' in this Library.");

                System.out.println("\n" + "=".repeat(50));

                if (user_data.getUserType().equalsIgnoreCase("librarian")) {
                    System.out.println("\nEnter 101\t-> To list all actions that a Librarian can perform");
                    // These will be the Operations that is Accessible only to the Librarian.
                    System.out.println("Enter 1\t-> To Show ALl books");
                    System.out.println("Enter 2\t-> To Show Only Deleted books");
                    System.out.println("Enter 3\t-> Add a New Book");
                    System.out.println("Enter 4\t-> Update a Book");
                    System.out.println("Enter 5\t-> Delete a Book");
                    System.out.println("Enter 6\t-> To show all Users");
                    System.out.println("Enter 7\t-> To show all Members");

                    System.out.print("\nPlease enter your choice: ");
                    int user_choice = sc.nextInt();
                    sc.nextLine();

                    BookDto book_dto = new BookDto();
                    BookDao book_dao = new BookDao();

                    if (user_choice == 3) {
                        // This block will be used to add a book into my DB

                        System.out.println("\n" + "-".repeat(50));
                        Book book_data = book_dto.getBookDetails();
                        try {
                            book_dao.librarianAddBook(book_data);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        System.out.println("\n" + "-".repeat(50));

                    } else if(user_choice == 4){
                        // This block will be to update all the books.

                        System.out.println("\n" + "-".repeat(50));
                        Book book_data = book_dto.getBookDetails();
                        try {
                            book_dao.librarianUpdateBook(book_data);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        System.out.println("\n" + "-".repeat(50));

                    } else if (user_choice == 5){
                        // This will be meant to delete the user.

                        System.out.println("\n" + "-".repeat(50));

                        // Get the ISBN from the user.
                        int isbn = book_dto.getBookIsbn();
                        try {
                            book_dao.librarianRemoveBook(isbn);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        System.out.println("\n" + "-".repeat(50));

                    } else {
                        System.out.println("ERROR: INVALID USER CHOICE\nPlease try again\n");
                    }

                } else {
                    System.out.println("Enter 102\t-> To list all actions that a Member can perform");
                    // These will be accessible only to user.

                }
            }

            System.out.println("\n");
        }
    }
}