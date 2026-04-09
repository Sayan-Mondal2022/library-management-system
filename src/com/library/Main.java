package com.library;

import com.library.models.Book;
import com.library.dao.BookDao;
import com.library.dto.RegistrationRequest;
import com.library.service.RegistrationService;
import com.library.util.InputHandler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Integer user_id = null;
        Scanner sc = new Scanner(System.in);

        while (user_id == null) {
            System.out.print("Existing user?\tEnter 1 to Login\nNew user?\tEnter 2 to Register\nWant to exit?\tEnter 3 to close!\nEnter your choice: ");
            int choice = sc.nextInt();

            if (choice < 1 || choice > 3) {
                System.out.println("\nInvalid entry! Please try again\n");
                continue;
            }

            if (choice == 1) {
                System.out.println("\nEnter your Login Credentials");

                String email, password;

                System.out.println("Enter a Email Id: (sample@email.com) ");
                email = sc.next();

                System.out.println("\nEnter your password: ");
                password = sc.next();

                // Using the EmailId I will fetch the User Details from my User Table.
                // Fetch the User Details using the Email_id.

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


                    // Once Registration is completed Ask the user to Login.

                } catch (RuntimeException e) {
                    System.err.println("Error" + e.getMessage());
                }

            } else {
                System.out.println("\nExiting the Library Management system...");
                break;
            }

            System.out.println("\n");
        }
    }
}