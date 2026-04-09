package com.library.models;

import java.time.LocalDateTime;

public class BookLoan {
    private int loan_id, user_id;
    private String barcode;
    private LocalDateTime issue_date, due_date, return_date;
    private String loan_type;   // Can be 'Rented' or 'Reserved'

    // due_days varies, by default it will be 14 days,
    public BookLoan(int user_id, String barcode, int due_days, String loan_type, LocalDateTime issue_date){
        this.user_id = user_id;
        this.barcode = barcode;
        this.loan_type = loan_type;
        this.issue_date = issue_date;
        this.due_date = this.issue_date.plusDays(due_days);   // Can take a book loan for 14 days
        this.return_date = null;
    }


    // Getters:
    public String getBarcode() {
        return this.barcode;
    }

    public String getLoan_type() {
        return this.loan_type;
    }

    public LocalDateTime getIssue_date() {
        return this.issue_date;
    }

    public LocalDateTime getDue_date() {
        return this.due_date;
    }

    public LocalDateTime getReturn_date() {
        return this.return_date;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public int getLoan_id() {
        return this.loan_id;
    }
}
