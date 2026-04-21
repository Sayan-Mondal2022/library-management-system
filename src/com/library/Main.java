package com.library;

import com.library.controller.*;
import com.library.dto.UserDto;
import com.library.util.Validators;

public class Main {
    public static void main(String[] args) {
        UserDto userData = null;

        while (userData == null) {
            System.out.print("\nExisting user?\tEnter 1 to Login\nNew user?\tEnter 2 to Register\nWant to exit?\tEnter 3 to close!");
            int choice = Validators.getValidInt("\nEnter your choice: ");

            if (choice < 1 || choice > 3) {
                System.out.println("\nInvalid entry! Please try again\n");
                continue;
            }

            if (choice == 1) {
                LoginController loginController = new LoginController();

                System.out.println("=".repeat(21) + " USER LOGIN " + "=".repeat(21));
                userData = loginController.userLogin();

                if (userData != null) {
                    System.out.println("\nUser Name is: " + userData.getUserName());
                    System.out.println("=".repeat(18) + " LOGIN SUCCESSFUL " + "=".repeat(18));
                } else
                    System.out.println("=".repeat(20) + " LOGIN FAILED " + "=".repeat(20));

            } else if (choice == 2) {
                RegistrationController registrationController = new RegistrationController();

                System.out.println("=".repeat(18) + " USER REGISTRATION " + "=".repeat(18));
                userData = registrationController.registerUser();

                if (userData != null) {
                    System.out.println("\nUser Name is: " + userData.getUserName());
                    System.out.println("=".repeat(15) + " REGISTRATION SUCCESSFUL " + "=".repeat(15));
                } else
                    System.out.println("=".repeat(17) + " REGISTRATION FAILED " + "=".repeat(17));

            } else {
                System.out.println("\nExiting the Library Management system...");
                break;
            }

            if (userData != null) {
                System.out.println("\n" + "=".repeat(54));
                System.out.println("=".repeat(8) + " WELCOME TO LIBRARY MANAGEMENT SYSTEM " + "=".repeat(8));

                System.out.println("\nGreetings! " + userData.getUserName());
                System.out.println("User Type: '" + userData.getUserType() + "'");

                System.out.println("\n" + "=".repeat(54));

                if (userData.getUserType().equalsIgnoreCase("librarian")) {

                } else if (userData.getUserType().equalsIgnoreCase("member")) {

                }
            }
            System.out.println("\n");
        }
    }
}