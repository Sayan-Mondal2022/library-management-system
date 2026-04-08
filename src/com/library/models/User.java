package com.library.models;

public class User {
    private int user_id;
    private String userName, address, email, phoneNo;

    // Getters
    public int getUser_id() {
        return this.user_id;
    }

    public String getAddress() {
        return this.address;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    // Setters
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
