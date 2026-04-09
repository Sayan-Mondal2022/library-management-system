package com.library;

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
    public static User userLogin(){
        try {
            LoginRequest request;

            while (true) {
                try {
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
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Integer user_id = null;
        Scanner sc = new Scanner(System.in);
        User user_data = null;

        while (user_id == null) {
            System.out.print("Existing user?\tEnter 1 to Login\nNew user?\tEnter 2 to Register\nWant to exit?\tEnter 3 to close!\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice < 1 || choice > 3) {
                System.out.println("\nInvalid entry! Please try again\n");
                continue;
            }

            if (choice == 1) {
                user_data = userLogin();
                user_id = user_data.getUser_id();

                if (user_data != null) {
                    System.out.println("User Name is: " + user_data.getUserName());
                    System.out.println("Login Successful!");
                }
                System.out.println("\n" + "=".repeat(30));

            } else if (choice == 2) {
                try {
                    // InputHandler will be a helper function which will get the userInputs for registration.
                    RegistrationRequest request;

                    while (true) {
                        try {
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
                    System.out.println("\n" + "=".repeat(30));

                    // User Login
                    user_data = userLogin();
                    user_id = user_data.getUser_id();

                    if (user_data != null) {
                        System.out.println("User Name is: " + user_data.getUserName());
                        System.out.println("Login Successful!");
                    }
                    System.out.println("\n" + "=".repeat(30));

                } catch (RuntimeException e) {
                    System.err.println("Error" + e.getMessage());
                }
            } else {
                System.out.println("\nExiting the Library Management system...");
                break;
            }

            // This is where the Library management work starts
//            while (user_id != null) {
//                pass;
//            }

            System.out.println("\n");
        }
    }
}