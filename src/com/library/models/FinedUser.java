package com.library.models;

// This table, will store users who are fined. Such that it becomes easy to maintain.
public class FinedUser {
    private int loan_id, user_id;
    private double fine;
    private String loan_status;    // Will be 'Open' if the is not returned even after the due_date, will be 'close' if returned after dur_date.
    private String due_status;     // Can be 'Cleared' or 'unCleared'

    public FinedUser(int loan_id, int user_id, double fine, String loan_status, String due_status){
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.fine = fine;
        this.loan_status = loan_status;
        this.due_status = due_status;
    }

    // Getters:
    public int getLoan_id() {
        return this.loan_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public double getFine() {
        return this.fine;
    }

    public String getLoan_status() {
        return this.loan_status;
    }

    public String getDue_status() {
        return this.due_status;
    }
}
