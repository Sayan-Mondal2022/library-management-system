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
import com.library.util.UserActions;

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
                        System.out.println("\nEnter 100\t-> To log out");
                        System.out.println("Enter 101\t-> To list all actions that a Librarian can perform");
                        UserActions.librarianOptionsList();

                        System.out.print("\nPlease enter your choice: ");
                        int user_choice = sc.nextInt();
                        sc.nextLine();

                        if (user_choice == 1 || user_choice == 2) {
                            // user_choice == 2 -> is_deleted = True
                            // user_choice == 1 -> is_deleted = False
                            ConsoleView.displayBooks(user_choice == 2);

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

                        } else if (user_choice == 9 || user_choice == 13) {
                            // ISBN
                            try {
                                String query = MenuController.getSearchQuery("isbn");
                                ConsoleView.displaySearchedBooks("isbn", query, user_choice == 13);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 10 || user_choice == 14) {
                            // author
                            try {
                                String query = MenuController.getSearchQuery("author");
                                ConsoleView.displaySearchedBooks("author", query, user_choice == 14);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 11 || user_choice == 15) {
                            // Title
                            try {
                                String query = MenuController.getSearchQuery("title");
                                ConsoleView.displaySearchedBooks("title", query, user_choice == 15);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 12 || user_choice == 16) {
                            // Genre
                            try {
                                String query = MenuController.getSearchQuery("genre");
                                ConsoleView.displaySearchedBooks("genre", query, user_choice == 16);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }


                        } else if (user_choice == 17) {
                            // Show a Detail of a Particular User
                            ConsoleView.displayUsers("allusers");
                            ConsoleView.displayUserDetails();

                        }
                        else if (user_choice == 18) {
                            // Show details of a Member
                            ConsoleView.displayUsers("member");
                            ConsoleView.displayUserDetails();

                        }
                        else if (user_choice == 19) {
                            // Show details of a Librarian
                            ConsoleView.displayUsers("librarian");
                            ConsoleView.displayUserDetails();

                        }

                        else if (user_choice == 20) {
                            // This is to Insert a BookItem
                            MenuController.insertBookItem();

                        } else if (user_choice == 24) {


                        } else if (user_choice == 100) {
                            // Logout the User
                            user_data = null;
                            System.out.println("Logging out...");

                        }
                        else if (user_choice == 101) {
                            // This will list all the Options That a Librarian can Perform
                            UserActions.librarianOptionsList();

                        } else {
                            System.out.println("ERROR: INVALID USER CHOICE\nPlease try again\n");
                        }
                    }
                } else {
                    while (user_data != null) {
                        System.out.println("\nEnter 100\t-> To log out");
                        System.out.println("Enter 101\t-> To list all actions that a Member can perform");
                        UserActions.memberOptionsList();


                        System.out.print("\nPlease enter your choice: ");
                        int user_choice = sc.nextInt();
                        sc.nextLine();

                        if (user_choice == 1) {
                            // This will show only the Available Books to the user
                            ConsoleView.displayBooks(false);

                        } else if (user_choice == 2) {
                            // ISBN
                            try {
                                String query = MenuController.getSearchQuery("isbn");
                                ConsoleView.displaySearchedBooks("isbn", query, false);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 3) {
                            // author
                            try {
                                String query = MenuController.getSearchQuery("author");
                                ConsoleView.displaySearchedBooks("author", query, false);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 4) {
                            // Title
                            try {
                                String query = MenuController.getSearchQuery("title");
                                ConsoleView.displaySearchedBooks("title", query, false);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 5) {
                            // Genre
                            try {
                                String query = MenuController.getSearchQuery("genre");
                                ConsoleView.displaySearchedBooks("genre", query, false);

                            } catch (RuntimeException e) {
                                System.err.println("ERROR: " + e.getMessage());
                            }

                        } else if (user_choice == 100) {
                            // Will log out the User
                            System.out.println("Logging out...");

                        } else if (user_choice == 101) {
                            UserActions.memberOptionsList();

                        }
                    }
                }
            }

            System.out.println("\n");
        }
    }
}