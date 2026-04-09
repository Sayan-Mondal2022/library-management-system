package com.library.dto;

public class RegistrationRequest {
    // This is for user basic details.
    private String user_name, address, phone_no;

    // This will be used for user Login and Giving access control.
    private String email, user_type, password_hash;


    // Using a Constructor to assign the values all at the same time, as these values won't get changed.
    public RegistrationRequest(String userName, String address, String email, String phoneNo, String user_type, String password_hash){
        this.user_name = userName;
        this.address = address;
        this.email = email;
        this.phone_no = phoneNo;

        this.user_type = user_type;
        this.password_hash = password_hash;
    }

    // Getters for User
    public String getPhoneNo() {
        return this.phone_no;
    }

    public String getUserName() {
        return this.user_name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPasswordHash() {
        return this.password_hash;
    }

    public String getUserType() {
        return this.user_type;
    }
}
