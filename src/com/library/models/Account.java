package com.library.models;

public class Account {
    private int account_id, user_id;
    private String account_type, passwordHash;

    // Getters
    public int getUser_id() {
        return this.user_id;
    }

    public int getAccount_id() {
        return this.account_id;
    }

    public String getAccount_type() {
        return this.account_type;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    // Setters
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
