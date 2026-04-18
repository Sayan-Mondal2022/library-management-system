package com.library.controller;

import com.library.dao.BorrowBookDao;
import com.library.dto.BorrowBookApplicantsDto;
import com.library.dto.BorrowBookDto;
import com.library.dto.BorrowResponseDto;
import com.library.dto.FinedDetailsDto;
import com.library.service.BorrowBookService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class BorrowBookController {
    private Scanner sc = new Scanner(System.in);
    BorrowBookService service = new BorrowBookService(new BorrowBookDao());


    private int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());

                if (value <= 0) {
                    System.out.println("Value must be positive!");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }


    private int readDays(String message) {
        while (true) {
            int days = readInt(message);

            if (days > 365) {
                System.out.println("Too many days! Max allowed is 365.");
                continue;
            }

            return days;
        }
    }




    public void displayApplicants(ArrayList<BorrowBookApplicantsDto> applicantList) throws RuntimeException{
        if (applicantList == null || applicantList.isEmpty()) {
            throw new RuntimeException("THERE's NO APPLICANTS RIGHT NOW");
        }

        System.out.println("The Applicants are: ");
        for (BorrowBookApplicantsDto applicant : applicantList) {
            System.out.println("Applicant ID: " + applicant.getUserId());
            System.out.println("Book Barcode: " + applicant.getBarcode());
        }
        System.out.println("\n");
    }

    private void displayIssuedBooks(ArrayList<BorrowBookDto> list) throws RuntimeException{
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("NO BOOKS FOUND!");
        }

        System.out.println("Total books retrieved are: " + list.size());
        System.out.println("Issued books are:");

        for (BorrowBookDto response : list) {

            System.out.println("\n");
            System.out.println("Borrow Id: " + response.getBorrowId());
            System.out.println("User Id: " + response.getUserId());
            System.out.println("Book Barcode: " + response.getBarcode());
            System.out.println("Book issued on: " + response.getIssueDate());
            System.out.println("Book due date: " + response.getDueDate());
            System.out.println(
                    "Is returned: " + (response.getReturnDate() != null ? ("Returned\nReturned on: " + response.getReturnDate()) : "Not Returned")
            );
        }

    }


    private void displayFinedUserDetails(ArrayList<FinedDetailsDto> responseList) throws RuntimeException {
        if (responseList == null || responseList.isEmpty()) {
            throw new RuntimeException("NO DATA FOUND!");
        }

        System.out.println("Total fined records: " + responseList.size());

        for (FinedDetailsDto dto : responseList) {

            System.out.println("\nFine ID: " + dto.getFineId());
            System.out.println("Borrow ID: " + dto.getBorrowId());
            System.out.println("User ID: " + dto.getUserId());
            System.out.println("Book Barcode: " + dto.getBarcode());

            System.out.println("Due Days: " + dto.getDueDays());

            System.out.println("Fine Amount: " + dto.getFineAmount());
            System.out.println("Paid Amount: " + dto.getPaidAmount());

            System.out.println("Payment Status: " + (dto.getIsPaid() ? "Paid" : "Pending"));

            System.out.println("Return Status: " +
                    (dto.getReturnDate() != null ? "Returned" : "Not Returned"));
        }
    }


    private void displayOverdueDetails(ArrayList<BorrowBookDto> responseList) throws RuntimeException {
        if (responseList == null || responseList.isEmpty()) {
            throw new RuntimeException("THERE's NO OVER DUE USERS");
        }

        System.out.println("Total OverDues are: " + responseList.size());
        System.out.println("Dues are:");

        for (BorrowBookDto dto : responseList) {
            System.out.println("\nBorrow ID: " + dto.getBorrowId());
            System.out.println("User ID: " + dto.getUserId());
            System.out.println("Book Barcode: " + dto.getBarcode());
            System.out.println("Due Days: " + dto.getDueDays());
        }

        System.out.println("\nList ended");
    }

    private int getUserId() throws RuntimeException{
        try {
            ArrayList<Integer> userIds = service.getAllUsers();

            if (userIds.isEmpty()) {
                throw new RuntimeException("THERE's NO OVERDUE USERs");
            }

            System.out.println("TOTAL USERS ARE: " + userIds.size());
            System.out.println("USER IDs are:");

            for (Integer userId : userIds) {
                System.out.println("USER ID: " + userId);
            }
            System.out.println("\n");

            int id = readInt("Enter USER ID: ");

            return id;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public void getAllReturnedBooks() {
        System.out.println("-".repeat(50));

        try {
            ArrayList<BorrowBookDto> list = service.getIssuedBooks(true);
            displayIssuedBooks(list);

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void getAllNonReturnedBooks() {
        System.out.println("-".repeat(50));

        try {
            ArrayList<BorrowBookDto> list = service.getIssuedBooks(false);
            displayIssuedBooks(list);

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void getAllBorrowedBooks() {
        System.out.println("-".repeat(50));

        try {
            ArrayList<BorrowBookDto> list = service.getAllIssuedBooks();
            displayIssuedBooks(list);

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    // This function is used to issue the book to the user, Will updates the DB.
    public void issueBook() {
        System.out.println("-".repeat(50));

        System.out.println("SELECT the APPLICANT-ID and Book BARCODE from the below list:\n");
        try {
            ArrayList<BorrowBookApplicantsDto> applicants = service.getPendingApplicants();
            displayApplicants(applicants);

            int id = readInt("Enter USER ID: ");

            System.out.print("Enter the Book Barcode: ");
            String barcode = sc.nextLine();

            BorrowBookDto dto = new BorrowBookDto();
            dto.setUserId(id);
            dto.setBarcode(barcode);

            int days = readDays("Enter borrow duration (days): ");
            dto.setIssueDate(LocalDateTime.now());
            dto.setDueDate(dto.getIssueDate().plusDays(days));

            service.issueBook(dto);

            System.out.println("Book has been issued!!");

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }

    public void rejectApplicant(){
        System.out.println("-".repeat(50));

        try {
            ArrayList<BorrowBookApplicantsDto> applicants = service.getPendingApplicants();
            displayApplicants(applicants);


            int id = readInt("Enter Applicant ID: ");

            System.out.print("Enter the Book Barcode: ");
            String barcode = sc.nextLine();

            service.rejectApplicant(id, barcode);
            System.out.println("APPLICANT WITH ID-" + id + " IS REJECTED");

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void fineAllUsers() {
        System.out.println("-".repeat(50));

        try {
            service.fineAllUsers();
            System.out.println("All users with Overdue has been fined\nUpdated in DB as well");

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void getAllFinedUsers() {
        System.out.println("-".repeat(50));

        try {
            ArrayList<FinedDetailsDto> responseList = service.getAllFinedUsers();
            displayFinedUserDetails(responseList);


        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void getAllOverdueUsers() {
        System.out.println("-".repeat(50));

        try {
            ArrayList<BorrowBookDto> responseList = service.getAllOverdueUsers();
            displayOverdueDetails(responseList);

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void fineUser() {
        System.out.println("-".repeat(50));

        try {
            int id = getUserId();

            ArrayList<BorrowBookDto> responseList = service.getOverdueUser(id);
            if (responseList.isEmpty()) {
                System.out.println("THERE's NO OVERDUE FOR THIS USER");
                return;
            }
            displayOverdueDetails(responseList);

            service.fineUser(id);
            System.out.println("USER HAS BEEN FINED!");

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void getFinedUser() {
        System.out.println("-".repeat(50));

        try {
            int id = getUserId();

            ArrayList<FinedDetailsDto> responseList = service.getFinedUser(id);
            displayFinedUserDetails(responseList);


        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void getUserOverdue() {
        System.out.println("-".repeat(50));

        try {
            int id = getUserId();

            ArrayList<BorrowBookDto> responseList = service.getOverdueUser(id);
            displayOverdueDetails(responseList);


        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }


    public void collectBook() {
        System.out.println("-".repeat(50));

        try {
            ArrayList<BorrowBookDto> list = service.getIssuedBooks(false);
            displayIssuedBooks(list);

            System.out.print("\nEnter the Borrow ID: ");
            int borrowId = Integer.parseInt(sc.nextLine());

            BorrowBookDto issuedBook = null;
            for (BorrowBookDto book : list) {
                if (book.getBorrowId() == borrowId) {
                    issuedBook = book;
                    break;
                }
            }

            if (issuedBook == null) {
                System.out.println("ERROR: BOOK IS EITHER COLLECTED ALREADY or BOOK DATA IS INVALID");
                return;
            }
            service.collectBook(issuedBook);
            System.out.println("BOOK IS COLLECTED BACK!");

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }

    public void getBooksIssuedToUser() {
        System.out.println("-".repeat(50));

        try {
            int userId = readInt("Enter USER ID: ");

            ArrayList<BorrowResponseDto> list = service.getBooksIssuedToUser(userId);

            if (list == null || list.isEmpty()) {
                System.out.println("NO BOOKS FOUND FOR THIS USER");
                return;
            }

            System.out.println("Books issued to User ID: " + userId);

            for (BorrowResponseDto dto : list) {

                System.out.println("\nBorrow ID: " + dto.getBorrowId());
                System.out.println("Title: " + dto.getTitle());
                System.out.println("ISBN: " + dto.getIsbn());
                System.out.println("Barcode: " + dto.getBarcode());
                System.out.println("Shelf: " + dto.getShelfId());

                System.out.println("Status: " + dto.getBookStatus());
                System.out.println("Condition: " + dto.getBookCondition());

                System.out.println("Issued On: " + dto.getIssueDate());
                System.out.println("Due Date: " + dto.getDueDate());

                System.out.println(
                        "Return Status: " +
                                (dto.getReturnDate() != null
                                        ? "Returned on " + dto.getReturnDate()
                                        : "Not Returned")
                );

                System.out.println("Due Days: " + dto.getDueDays());
            }

        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }
}
