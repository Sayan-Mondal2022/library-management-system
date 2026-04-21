package com.library.controller;

import com.library.dto.LoginRequest;
import com.library.dto.UserDto;
import com.library.service.LoginService;
import com.library.util.PasswordUtil;
import com.library.util.Validators;

public class LoginController {
    private final LoginService loginService = new LoginService();

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
        } catch (RuntimeException e) {
            System.err.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    private LoginRequest getLoginDetails() {
        String email = Validators.getValidEmail();
        String password = Validators.getValidPassword();

        return new LoginRequest(email, password);
    }
}