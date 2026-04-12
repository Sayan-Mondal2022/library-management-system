package com.library.util;


// This class will have all the Options list that are available for "Member" and "Librarian"
public class UserActions {
    public static void librarianOptionsList(){
        System.out.println("\n" + "*".repeat(50));
        System.out.println("\n\n");

        // These will be the Operations that is Accessible only to the Librarian.
//        System.out.println("Enter 01\t-> Show ALl books");
//        System.out.println("Enter 02\t-> Show Only Deleted books");
//
//        System.out.println("Enter 03\t-> Add a New Book");
//        System.out.println("Enter 04\t-> Update a Book");
//        System.out.println("Enter 05\t-> Delete a Book");

        // This will show NON-DELETED Books
//        System.out.println("Enter 09\t-> Search a book by ISBN");
//        System.out.println("Enter 10\t-> Search a book by Author");
//        System.out.println("Enter 11\t-> Search a book by Title");
//        System.out.println("Enter 12\t-> Search a book by Genre");

        // This will show DELETED Books
//        System.out.println("Enter 13\t-> Search a deleted book by ISBN");
//        System.out.println("Enter 14\t-> Search a deleted book by Author");
//        System.out.println("Enter 15\t-> Search a deleted book by Title");
//        System.out.println("Enter 16\t-> Search a deleted book by Genre");

        // ----------------------------
        // Actions performed on Users
//        System.out.println("Enter 06\t-> List all Users");
//        System.out.println("Enter 07\t-> List all Members");
//        System.out.println("Enter 08\t-> List all Librarians");
//
//        System.out.println("Enter 17\t-> Show details of a user");
//        System.out.println("Enter 18\t-> Show details of a member");
//        System.out.println("Enter 19\t-> Show details of a librarian");

//        System.out.println("Enter \t-> To blacklist a user");
//        System.out.println("Enter \t-> To get Details of a blacklisted user");
//        System.out.println("Enter \t-> List all blacklisted users");

        // ----------------------------------
        // Based on BookLoan (Borrowing the Books from Library)
//        System.out.println("Enter \t-> Show a fined user");
//        System.out.println("Enter \t-> Show all Fined Users");
//
//        System.out.println("Enter \t-> Calculate fine of a user");
//        System.out.println("Enter \t-> Calculate fine of all users");
//
//        System.out.println("Enter \t-> Check dues of a user (having UnCleared Dues)");
//        System.out.println("Enter \t-> Check dues of a user (have Cleared all Dues)");
//        System.out.println("Enter \t-> Check dues of All Users (having UnCleared Dues)");
//        System.out.println("Enter \t-> Check dues of All Users (have Cleared all Dues)");
//
//        System.out.println("Enter \t-> Check status of a Book Loan");
//        System.out.println("Enter \t-> Check status of all Book Loans");
//
//        System.out.println("Enter \t-> List all borrowed books by a user");
//        System.out.println("Enter \t-> List all borrowed books");
//
//        System.out.println("Enter \t-> List all reserved books by a user");
//        System.out.println("Enter \t-> List all reserved books by all users");
//
//        System.out.println("Enter \t-> List all returned books by a user");
//        System.out.println("Enter \t-> List all returned books");

        // ---------------------------------
        // Actions that a Librarian can perform on a Book Item
        System.out.println("Enter 20\t-> Add a BookItem");
        System.out.println("Enter 22\t-> Update a BookItem");
        System.out.println("Enter 23\t-> Remove a BookItem");
//
//        // Listing all the Book Copies.
        System.out.println("Enter 24\t-> List all BookItems");
        System.out.println("Enter 25\t-> List all BookItems belong to a Author");
        System.out.println("Enter 26\t-> List all BookItems falls under a Genre");
        System.out.println("Enter 27\t-> List all BookItems within a Shelf");
        System.out.println("Enter 28\t-> List all BookItems within a Section");
//
//        // To list all copies belong to a Single Metadata
//        System.out.println("Enter \t-> List all BookItems based on ISBN");
//        System.out.println("Enter \t-> List all BookItems based on Book Title");
//
//        // To check the status of a BookItem (This will show -> whether the book is Available, Loaned(Rented) or Reserved)
//        System.out.println("Enter \t-> Check the status of BookItems based on Barcode");
//        System.out.println("Enter \t-> Check the status of BookItems based on ISBN");
//        System.out.println("Enter \t-> Check the status of BookItems based on Author");
//        System.out.println("Enter \t-> Check the status of BookItems based on Genre");
//        System.out.println("Enter \t-> Check the status of BookItems based on Shelf_id");
//        System.out.println("Enter \t-> Check the status of BookItems based on Section");

        // ------------------
        // To Log Out the User
        System.out.println("\nEnter 100\t-> To log out");

        System.out.println("\n\n" + "*".repeat(50));
    }

    public static void memberOptionsList(){
        System.out.println("\n" + "*".repeat(50));
        System.out.println("\n\n");

        // These will be accessible only to user.
        System.out.println("Enter \t-> To update your details ");

        // --------------------------
        // Actions that can be performed with Books
        System.out.println("Enter 01\t-> To Show ALl books");
        System.out.println("Enter 02\t-> Search a book by ISBN");
        System.out.println("Enter 03\t-> Search a book by Author");
        System.out.println("Enter 04\t-> Search a book by Title");
        System.out.println("Enter 05\t-> Search a book by Genre");

        // ----------------------------
        // Actions that can be performed with BookItems
        System.out.println("Enter \t-> List all BookItems");
        System.out.println("Enter \t-> List all BookItems belong to a Author");
        System.out.println("Enter \t-> List all BookItems falls under a Genre");
        System.out.println("Enter \t-> List all BookItems within a Shelf");
        System.out.println("Enter \t-> List all BookItems within a Section");

        // To list all copies belong to a Single Metadata
        System.out.println("Enter \t-> List all BookItems based on ISBN");
        System.out.println("Enter \t-> List all BookItems based on Book Title");


        // -----------------------
        // Actions with Book Loan
        System.out.println("Enter \t-> Borrow a book");
        System.out.println("Enter \t-> Reserve a book");
        System.out.println("Enter \t-> Return a book");
        System.out.println("Enter \t-> List all borrowed books");
        System.out.println("Enter \t-> List all reserved books");
        System.out.println("Enter \t-> List all returned books");

        System.out.println("Enter \t-> Show your fine for a book");
        System.out.println("Enter \t-> Show your fine for all books");
        System.out.println("Enter \t-> Clear a due");
        System.out.println("Enter \t-> List all cleared dues");
        System.out.println("Enter \t-> List all un-cleared dues");

        // -----------------------
        // To Log out
        System.out.println("\nEnter 100\t-> To log out");

        System.out.println("\n\n" + "*".repeat(50));
    }
}
