package com.library.controller;

import com.library.dto.RegistrationRequest;
import com.library.dto.UserDto;
import com.library.models.User;
import com.library.service.RegistrationService;
import com.library.util.Validators;

public class RegistrationController {
    private final RegistrationService registrationService = new RegistrationService();

    public UserDto registerUser() {
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
        RegistrationRequest request = new RegistrationRequest();

        request.setUserName(Validators.getValidUserName());
        request.setPhoneNo(Validators.getValidPhoneNo());
        request.setAddress(Validators.getValidAddress());
        request.setEmail(Validators.getValidEmail());
        request.setPassword(Validators.getValidPassword());
        request.setUserType(Validators.getValidUserType());

        System.out.println("\nEntered Details:");
        System.out.println("User name: " + request.getUserName());
        System.out.println("Phone Number: " + request.getPhoneNo());
        System.out.println("Address: " + request.getAddress());
        System.out.println("Email Id: " + request.getEmail());
        System.out.println("Password: " + "*".repeat(request.getPassword().length()));
        System.out.println("User Type: " + request.getUserType());

        return request;
    }
}