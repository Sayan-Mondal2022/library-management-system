package com.library.util;

import java.util.Scanner;

public class Validators {
    private static final Scanner sc = new Scanner(System.in);
    private Validators(){}

    public static int getValidInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());

                if (value <= 0) {
                    System.out.println("Value must be positive!");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }

    public static int getValidDays(String message) {
        while (true) {
            int days = getValidInt(message);

            if (days > 365) {
                System.out.println("Too many days! Max allowed is 365.");
                continue;
            }

            return days;
        }
    }


    private static void validatePhoneNo(String phoneNo) throws IllegalArgumentException {
        if (phoneNo.isEmpty())
            throw new IllegalArgumentException("Phone number can't be empty");

        if (phoneNo.length() != 10)
            throw new IllegalArgumentException("Phone number must be exactly 10 digits");
    }

    private static void validateEmail(String email) throws IllegalArgumentException {
        if (email.isEmpty())
            throw new IllegalArgumentException("Email can't be empty");

        if (email.length() < 5 || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email format");
    }

    private static void validatePassword(String password) throws IllegalArgumentException {
        if (password.isEmpty())
            throw new IllegalArgumentException("Password can't be empty");

        if (password.length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters");
    }

    public static void validateUserName(String userName) throws IllegalArgumentException {
        if (userName.isEmpty())
            throw new IllegalArgumentException("User name can't be empty");

        if (userName.length() < 2)
            throw new IllegalArgumentException("User name length should be more than 1 character");
    }


    private static void validateAddress(String address) throws IllegalArgumentException {
        if (address.isEmpty())
            throw new IllegalArgumentException("Address can't be empty");

        if (address.length() < 5)
            throw new IllegalArgumentException("Address should be valid");
    }


    public static String getValidUserName() {
        while (true) {
            System.out.print("Enter User Name: ");
            String userName = sc.nextLine().trim();
            try {
                validateUserName(userName);
                return userName;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getValidPassword() {
        while (true) {
            System.out.print("Enter Password: ");
            String password = sc.nextLine().trim();
            try {
                validatePassword(password);
                return password;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getValidEmail() {
        while (true) {
            System.out.print("Enter Email Id: ");
            String email = sc.nextLine().trim();
            try {
                validateEmail(email);
                return email;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getValidPhoneNo(){
        while (true) {
            System.out.print("Enter the phone number (1234567891): ");
            String phoneNo = sc.nextLine().trim();
            try {
                validatePhoneNo(phoneNo);

                if (phoneNo.startsWith("+91"))
                    phoneNo = phoneNo.replace("+91", "");

                return phoneNo;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getValidAddress() {
        while (true) {
            System.out.print("Enter the house address: ");
            String address = sc.nextLine().trim();
            try {
                validateAddress(address);
                return address;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getValidUserType(){
        while (true) {
            System.out.print("\nChoose user type:\n1. Librarian\n2. Member\nEnter choice: ");
            int userTypeChoice = Integer.parseInt(sc.nextLine());

            if (userTypeChoice != 1 && userTypeChoice != 2) {
                System.out.println("Invalid choice");
                continue;
            }

            return (userTypeChoice == 1) ? "librarian" : "member";
        }
    }
}
