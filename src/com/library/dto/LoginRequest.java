package com.library.dto;

public class LoginRequest {
    private String email, password;

    public LoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
