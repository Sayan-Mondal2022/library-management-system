package com.library.dao;

import com.library.models.BookLoan;

public class BookLoanDao {
    public void userAddBookLoan(BookLoan obj) {
        // Logic to add the data into my db
        String sqlQuery = "INSERT INTO BookLoan (user_id, barcode, issue_date, due_date, return_date) VALUES (?, ?, ?, ?, ?)";
    }

    public void userReturnBook(int loan_id) {
        // Retrieve the data, and then update the return_date column with LocalDateTime.now().
        // RUN a update query for the BookLoan Table
        // Change the loan_status to 'closed' in FinedUser Table (If the user was fined).
    }

    public void userShowFine(int user_id) {
        // Runs a SELECT query on FinedUsers table, if found will add up and show the total fine.
        // If the user is not found, then check with the BookLoan table once, to confirm
        double fine = 0.0;

        if (fine > 0) System.out.println("Your fine amount is: " + fine);
        else System.out.println("You have cleared all your dues");
    }

    public void librarianShowLoanedBooks() {
        // SELECT query to show all the books that are loaned.
    }

    public void librarianShowBooksReturned(){
        // return_date will not be null
    }

    public void librarianShowBooksNotReturned(){
        // return_date will be null
    }

    public void librarianShowFinedUsers() {
        // SELECT Query on FinedUsers Table
    }

    public void librarianShowFinedUsers(String loan_status) {
        // SELECT Query on FinedUsers Table
    }

    public void librarianShowFinedUsers(String loan_status, String due_staus) {
        // SELECT Query on FinedUsers Table
    }

    // This function will run for all the loan
    public void librarianCalculateFine(){

    }

    // This function will run only for the given loan_id
    public void librarianCalculateFine(int loan_id) {
        double fine = 0.0;

        // Perform a SELECT query to get the Loan
        String sqlQuery = "SELECT * FROM BookLoan WHERE loan_id = ? LIMIT 1";

        // Perform a SELECT Query on Fined User Table, if the user exist, then update the fine else add the user.
        // getUserQuery if not found
        // addUserQuery

        // print the fine amount.
    }

    // This will calculate the fine for the given User.
    public void librarianCalculateFineByUserId(int user_id){

    }
}
