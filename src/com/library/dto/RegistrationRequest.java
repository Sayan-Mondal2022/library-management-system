package com.library.dto;

public class RegistrationRequest {
    // This is for User Class
    private String userName, address, email, phoneNo;

    // This will be for Account Class
    private String accountType, passwordHash;


    // Using a Constructor to assign the values all at the same time, as these values won't get changed.
    public RegistrationRequest(String userName, String address, String email, String phoneNo, String accountType, String passwordHash){
        this.userName = userName;
        this.address = address;
        this.email = email;
        this.phoneNo = phoneNo;

        this.accountType = accountType;
        this.passwordHash = passwordHash;
    }


    // Getters for User
    public String getPhoneNo() {
        return this.phoneNo;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    // Getters for Account
    public String getPasswordHash() {
        return this.passwordHash;
    }

    public String getAccountType() {
        return this.accountType;
    }
}
