package com.library.controller;

import com.library.dto.LoginRequest;
import com.library.dto.UserDto;
import com.library.service.LoginService;
import com.library.util.PasswordUtil;

import java.util.Scanner;

public class LoginController {
    private final LoginService loginService = new LoginService();
    private final Scanner sc = new Scanner(System.in);

    public UserDto userLogin() {
        LoginRequest request;

        while (true) {
            try {
                request = getLoginDetails();
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again...\n");
            }
        }

        try {
            return loginService.loginUser(request);
        } catch (IllegalArgumentException e) {
            System.err.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    private LoginRequest getLoginDetails() {
        System.out.println("Enter your Login Credentials\n");

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        validateEmail(email);

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();
        validatePassword(password);

        return new LoginRequest(email, password);
    }

    private void validateEmail(String email) {
        if (email.isEmpty())
            throw new IllegalArgumentException("Email can't be empty");

        if (email.length() < 5 || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email format");
    }

    private void validatePassword(String password) {
        if (password.isEmpty())
            throw new IllegalArgumentException("Password can't be empty");

        if (password.length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters");
    }
}