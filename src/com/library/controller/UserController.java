package com.library.controller;

import com.library.dto.UserSummaryDto;
import com.library.dto.UserDto;
import com.library.service.UserService;
import com.library.util.Validators;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final Scanner sc = new Scanner(System.in);
    private final UserService service = new UserService();

    private boolean askYesNo(String message) {
        System.out.print(message);
        return sc.nextLine().trim().equalsIgnoreCase("yes");
    }

    private UserDto getUpdatedDetails() throws IllegalArgumentException{
        UserDto user = new UserDto();
        System.out.println("Enter (yes/no) to update the details\n");

        if (askYesNo("Update User Name? ")) {
            user.setUserName(Validators.getValidUserName());
        }

        if (askYesNo("Update Email Id? ")) {
            user.setEmail(Validators.getValidEmail());
        }

        if (askYesNo("Update Password? ")) {
            user.setPassword(Validators.getValidPassword());
        }

        if (askYesNo("Update Address? ")) {
            user.setAddress(Validators.getValidAddress());
        }

        if (askYesNo("Update Phone No? ")) {
            user.setPhoneNo(Validators.getValidPhoneNo());
        }

        return user;
    }


    public void updateUserDetails(UserDto userData){
        System.out.println("-".repeat(50));

        try {
            UserDto existingDetails = service.getUser(userData.getUserId());
            UserDto updatedDetails = getUpdatedDetails();

            service.updateUserDetails(existingDetails, updatedDetails);
            System.out.println("\nUser details have been updated!");

        } catch (SQLException | RuntimeException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }

    private void displaySummary(int userId) throws SQLException {
        UserSummaryDto dto = service.getUserSummary(userId);

        if (dto == null) {
            System.out.println("User not found");
            return;
        }

        System.out.println("\n" + "=".repeat(18)+ " USER SUMMARY " + "=".repeat(18));
        System.out.println("\nUser ID: " + dto.getUserId());
        System.out.println("Name: " + dto.getUserName());
        System.out.println("Email: " + dto.getEmail());
        System.out.println("Phone: " + dto.getPhoneNo());
        System.out.println("Address: " + dto.getAddress());
        System.out.println("User Type: " + dto.getUserType());

        System.out.println("Total Borrowed Books: " + dto.getTotalBorrowedBooks());
        System.out.println("Total Returned Books: " + dto.getTotalReturnedBooks());

        System.out.println("Total Fine: " + dto.getTotalFine());
        System.out.println("Total Paid: " + dto.getTotalPaid());

        System.out.println("Blacklisted: " + dto.isBlacklisted());
        System.out.println("Reason: " + dto.getBlacklistReason());
        System.out.println("\n" + "=".repeat(15)+ " END OF USER SUMMARY " + "=".repeat(15));

    }

    private int selectUserAndDisplaySummary(String fetchType) throws SQLException {
        List<UserDto> users;
        if (fetchType.equalsIgnoreCase("allusers"))
            users = service.getAllUsers(fetchType);
        else
            users = service.fetchNonBlacklistedUsers();
        displayUsers(users, "partial");


        System.out.print("\nEnter the User Id: ");
        int userId = Integer.parseInt(sc.nextLine());
        displaySummary(userId);

        return userId;
    }

    public void getUserSummary(UserDto userData){
        System.out.println("-".repeat(50));

        try {
            if (userData.getUserType().equalsIgnoreCase("member"))
                displaySummary(userData.getUserId());
            else
                selectUserAndDisplaySummary("allusers");

        } catch (SQLException | RuntimeException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }

    public void blacklistUser(){
        System.out.println("-".repeat(50));

        try {
            int userId = selectUserAndDisplaySummary("nonBlacklistedUser");

            System.out.println("\n");
            System.out.print("Enter (yes/no) to blacklist user: ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                System.out.println("Enter the reason for blacklisting: ");
                String reason = sc.nextLine();

                service.blacklistUser(userId, reason);
                System.out.println("User has been Blacklisted");
            }

        } catch (SQLException | RuntimeException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(50));
    }

    public void getAllUsers(){
        System.out.println("-".repeat(60));

        try {
            System.out.println("""
                    Enter,
                    1 -> To get all Users
                    2 -> To get all members
                    3 -> To get all librarian""");

            int choice = Validators.getValidInt("\nEnter your choice: ");
            String userType = "allusers";

            if (choice == 1)
                userType = "member";
            else if (choice == 2) {
                userType = "librarian";
            }
            List<UserDto> users = service.getAllUsers(userType);
            displayUsers(users, "full");

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("-".repeat(60));
    }


    private void displayUsers(List<UserDto> users, String query) {
        if (users.isEmpty())
            throw new RuntimeException("USERS NOT FOUND");

        System.out.println("\n" + "=".repeat(18) + " USERS " + "=".repeat(18));
        for (UserDto user : users){
            System.out.println("\nUser Id: " + user.getUserId());
            System.out.println("User Name: " + user.getUserName());

            if (query.equalsIgnoreCase("full")) {
                System.out.println("User type: " + user.getUserType());
                System.out.println("Email id: " + user.getEmail());
                System.out.println("Phone No: " + user.getPhoneNo());
            }
        }
        System.out.println("\n" + "=".repeat(12) + " END OF USERS LIST " + "=".repeat(12));
    }
}
