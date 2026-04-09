package com.library.util;

import com.library.dto.LoginRequest;
import com.library.dto.RegistrationRequest;
import java.util.Scanner;

public class InputHandler {
    public static Scanner sc = new Scanner(System.in);

    public static LoginRequest getLoginDetails(){
        String email, password;

        System.out.println("\n" + "=".repeat(30));
        System.out.println("Enter your Login Credentials\n");

        System.out.println("Enter the Email Id: ");
        email = sc.nextLine().trim();

        if (email.isEmpty())
            throw new RuntimeException("Email can't be empty");
        else if (email.length() < 5 || !email.contains("@"))
            throw new RuntimeException("Invalid Email id");

        System.out.println("\nEnter the password: ");
        password = sc.nextLine().trim();

        if (password.isEmpty())
            throw new RuntimeException("Password can't be empty");
        else if (password.length() < 6)
            throw new RuntimeException("Password length is not satisfied");

        System.out.println("\n\nYour Login Credentials are:");
        System.out.println("Email Id: " + email);
        System.out.println("Password: " + password);

        return new LoginRequest(email, password);
    }

    public static RegistrationRequest getUserInput(){
        String user_name, phone_no, address;
        String email, password, user_type;
        int user_type_choice;

        System.out.println("\n\nEnter the Details for User Registration:\n");

        System.out.println("Enter the user name: ");
        user_name = sc.nextLine().trim();

        if (user_name.isEmpty()) {
            throw new RuntimeException("User name can't be empty");
        } else if (user_name.length() < 2)
            throw new RuntimeException("User name length should be more than 1 character");

        System.out.println("\nEnter the phone number (1234567891, should not begin with +91!): ");
        phone_no = sc.nextLine().trim();

        if (phone_no.isEmpty())
            throw new RuntimeException("Phone number can't be empty");
        if (phone_no.startsWith("+91"))
            phone_no = phone_no.replace("+91", "");
        if (phone_no.length() != 10) {
            throw new RuntimeException("Phone number can't be more or less than 10 digits");
        }

        System.out.println("\nEnter the house address: ");
        address = sc.nextLine().trim();

        if (address.isEmpty())
            throw new RuntimeException("Address can't be null");
        else if (address.length() < 5)
            throw new RuntimeException("Address should be valid!");

        System.out.println("\nEnter the Email Id (sample@email.com): ");
        email = sc.nextLine().trim();

        if (email.isEmpty())
            throw new RuntimeException("Email can't be empty");
        else if (email.length() < 5 || !email.contains("@"))
            throw new RuntimeException("Invalid Email id");

        System.out.println("\nEnter the Password (should be minimum of 6 chars): ");
        password = sc.nextLine().trim();

        if (password.isEmpty())
            throw new RuntimeException("Password can't be empty");
        else if (password.length() < 6)
            throw new RuntimeException("Password length is not satisfied");

        System.out.print("\nChoose the user type\nEnter '1' for 'Librarian' role\nEnter '2' for 'Member' role\nEnter the role: ");
        user_type_choice = sc.nextInt();

        if (!(user_type_choice >= 1 && user_type_choice <= 2))
            throw new RuntimeException("Invalid choice");

        if (user_type_choice == 1) user_type = "Librarian";
        else user_type = "Member";

        // Displaying the USER Details at last:
        System.out.println("\n" + "=".repeat(30));
        System.out.println("The Entered user details are:");
        System.out.println("\nUser name: " + user_name);
        System.out.println("Phone Number: " + phone_no);
        System.out.println("Address: " + address);
        System.out.println("Email Id: " + email);
        System.out.println("Password: " + "*".repeat(password.length()));
        System.out.println("User Type: " + user_type);
        System.out.println("\nThe following data will be stored in the DB!");

        return new RegistrationRequest(user_name, address, email, phone_no, user_type, password);
    }
}
