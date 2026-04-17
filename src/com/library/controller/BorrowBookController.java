package com.library.controller;

import com.library.dto.BorrowBookApplicantsDto;
import com.library.dto.BorrowBookDto;
import com.library.service.BorrowBookService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class BorrowBookController {
    private Scanner sc = new Scanner(System.in);
    private BorrowBookService service = new BorrowBookService();

    public void issueBook() {
        System.out.println("-".repeat(50));

        System.out.println("SELECT the APPLICANT-ID and Book BARCODE from the below list:\n");
        try {
            ArrayList<BorrowBookApplicantsDto> applicantList = service.getPendingApplicants();

            if (applicantList == null || applicantList.isEmpty()) {
                System.err.println("THERE's NO APPLICANTS RIGHT NOW");
                return;
            }

            System.out.println("The Applicants are: ");
            for (BorrowBookApplicantsDto applicant : applicantList) {
                System.out.println("Applicant ID: " + applicant.getUserId());
                System.out.println("Book Barcode: " + applicant.getBarcode());
            }
            System.out.println("\n");

            System.out.print("Enter the APPLICANT-ID: ");
            int id = (Integer.parseInt(sc.nextLine()));


            String barcode = applicantList.stream()
                    .filter(a -> a.getUserId() == id)
                    .map(BorrowBookApplicantsDto::getBarcode)
                    .findFirst()
                    .orElse(null);

            if (barcode == null) {
                System.err.println("INVALID APPLICANT DETAILS!");
                return;
            }

            BorrowBookDto dto = new BorrowBookDto();

            dto.setUserId(id);
            dto.setBarcode(barcode);

            System.out.print("Enter the borrow duration (in days): ");
            int days = Integer.parseInt(sc.nextLine());

            dto.setIssueDate(LocalDateTime.now());
            dto.setDueDate(dto.getIssueDate().plusDays(days));

            // Calling the Service to issue the Book.
            service.issueBook(dto);

            System.out.println("Book has been issued!!");

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }


        System.out.println("-".repeat(50));
    }

    public void showIssuedBooks() {
        System.out.println("-".repeat(50));

        try {
            ArrayList<BorrowBookDto> list = service.getIssuedBooks();

            if (list == null || list.isEmpty()) {
                System.out.println("NO DATA FOUND!");
                return;
            }

            System.out.println("Total books issued are: " + list.size());
            System.out.println("Issued books are:");

            for (BorrowBookDto response : list) {

                System.out.println("\n");
                System.out.println("Borrow Id: " + response.getBorrowId());
                System.out.println("User Id: " + response.getUserId());
                System.out.println("Book Barcode: " + response.getBarcode());
                System.out.println("Book issued on: " + response.getIssueDate());
                System.out.println("Book due date: " + response.getDueDate());
                System.out.println(
                        "Is returned: " + (response.getReturnDate() != null ? "Returned" : "Not Returned")
                );

            }

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }
}
