package com.library.controller;

import com.library.dto.RegistrationRequest;
import com.library.models.User;
import com.library.service.RegistrationService;

import java.util.Scanner;

public class RegistrationController {
    private final RegistrationService registrationService = new RegistrationService();
    private final Scanner sc = new Scanner(System.in);

    public User registerUser() {
        RegistrationRequest request;

        while (true) {
            try {
                request = getUserInput();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again...\n");
            }
        }

        try {
            return registrationService.registerUser(request);

        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            return null;
        }
    }

    private RegistrationRequest getUserInput() {
        String userName, phoneNo, address;
        String email, password, userType;
        int userTypeChoice;

        System.out.println("Enter the Details for User Registration:\n");

        System.out.print("Enter the user name: ");
        userName = sc.nextLine().trim();

        if (userName.isEmpty())
            throw new IllegalArgumentException("User name can't be empty");
        if (userName.length() < 2)
            throw new IllegalArgumentException("User name length should be more than 1 character");

        System.out.print("\nEnter the phone number (1234567891): ");
        phoneNo = sc.nextLine().trim();

        if (phoneNo.isEmpty())
            throw new IllegalArgumentException("Phone number can't be empty");

        if (phoneNo.startsWith("+91"))
            phoneNo = phoneNo.replace("+91", "");

        if (phoneNo.length() != 10)
            throw new IllegalArgumentException("Phone number must be exactly 10 digits");

        System.out.print("\nEnter the house address: ");
        address = sc.nextLine().trim();

        if (address.isEmpty())
            throw new IllegalArgumentException("Address can't be empty");
        if (address.length() < 5)
            throw new IllegalArgumentException("Address should be valid");

        System.out.print("\nEnter the Email Id: ");
        email = sc.nextLine().trim();

        if (email.isEmpty())
            throw new IllegalArgumentException("Email can't be empty");
        if (email.length() < 5 || !email.contains("@"))
            throw new IllegalArgumentException("Invalid Email id");

        System.out.print("\nEnter the Password (min 6 chars): ");
        password = sc.nextLine().trim();

        if (password.isEmpty())
            throw new IllegalArgumentException("Password can't be empty");
        if (password.length() < 6)
            throw new IllegalArgumentException("Password length is not satisfied");

        System.out.print("\nChoose user type:\n1. Librarian\n2. Member\nEnter choice: ");
        userTypeChoice = sc.nextInt();
        sc.nextLine();

        if (userTypeChoice != 1 && userTypeChoice != 2)
            throw new IllegalArgumentException("Invalid choice");

        userType = (userTypeChoice == 1) ? "librarian" : "member";

        System.out.println("\nEntered Details:");
        System.out.println("User name: " + userName);
        System.out.println("Phone Number: " + phoneNo);
        System.out.println("Address: " + address);
        System.out.println("Email Id: " + email);
        System.out.println("Password: " + "*".repeat(password.length()));
        System.out.println("User Type: " + userType);

        RegistrationRequest request = new RegistrationRequest();

        request.setUserName(userName);
        request.setPhoneNo(phoneNo);
        request.setAddress(address);
        request.setEmail(email);
        request.setPassword(password);
        request.setUserType(userType);

        return request;
    }
}