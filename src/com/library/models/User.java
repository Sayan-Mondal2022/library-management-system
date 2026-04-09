package com.library.models;

public class User {
    private int user_id;

    // Will have the basic user details.
    private String user_name, address, phone_no;

    // These will be used for Login, and giving the Access as per the user_type.
    private String email, password_hash, user_type;

    // Constructor used while creating the user.
    public User(String user_name, String address, String phone_no, String email, String password_hash, String user_type){
        this(-1, user_name, address, phone_no, email, password_hash, user_type);
    }

    // Used while adding the data into my DB
    public User(int user_id, String user_name, String address, String phone_no, String email, String password_hash, String user_type){
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_type = user_type;
        this.phone_no = phone_no;
        this.address = address;
        this.email = email;
        this.password_hash = password_hash;
    }

    // Getters
    public int getUser_id() {
        return this.user_id;
    }

    public String getAddress() {
        return this.address;
    }

    public String getUserName() {
        return this.user_name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNo() {
        return this.phone_no;
    }

    public String getPasswordHash() {
        return this.password_hash;
    }

    public String getUser_type() {
        return this.user_type;
    }
}
