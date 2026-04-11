package com.library.util;


// This class will have all the Options list that are available for "Member" and "Librarian"
public class UserActions {
    public static void librarianOptionsList(){
        System.out.println("\n" + "*".repeat(50));

        // These will be the Operations that is Accessible only to the Librarian.
        System.out.println("\n\nEnter 01\t-> To Show ALl books");
        System.out.println("Enter 02\t-> To Show Only Deleted books");

        System.out.println("Enter 03\t-> Add a New Book");
        System.out.println("Enter 04\t-> Update a Book");
        System.out.println("Enter 05\t-> Delete a Book");

        // This will show NON-DELETED Books
        System.out.println("Enter 09\t-> To search a book by ISBN");
        System.out.println("Enter 10\t-> To search a book by Author");
        System.out.println("Enter 11\t-> To search a book by Title");
        System.out.println("Enter 12\t-> To search a book by Genre");

        // This will show DELETED Books
        System.out.println("Enter 13\t-> To search a deleted book by ISBN");
        System.out.println("Enter 14\t-> To search a deleted book by Author");
        System.out.println("Enter 15\t-> To search a deleted book by Title");
        System.out.println("Enter 16\t-> To search a deleted book by Genre");

        // ----------------------------
        // Actions performed on Users
        System.out.println("Enter 06\t-> To list all Users");
        System.out.println("Enter 07\t-> To list all Members");
        System.out.println("Enter 08\t-> To list all Librarians");

        System.out.println("Enter \t-> To show details of a user");
        System.out.println("Enter \t-> To show details of a member");
        System.out.println("Enter \t-> To show details of a librarian");

        System.out.println("Enter \t-> To blacklist a user");
        System.out.println("Enter \t-> To details of a blacklisted user");
        System.out.println("Enter \t-> To list all blacklisted users");

        // ----------------------------------
        // Based on BookLoan (Borrowing the Books from Library)
        System.out.println("Enter \t-> To show a fined user");
        System.out.println("Enter \t-> To show all Fined Users");

        System.out.println("Enter \t-> To calculate fine of a user");
        System.out.println("Enter \t-> To calculate fine of all users");

        System.out.println("Enter \t-> To check dues of a user (having UnCleared Dues)");
        System.out.println("Enter \t-> To check dues of a user (have Cleared all Dues)");
        System.out.println("Enter \t-> To check dues of All Users (having UnCleared Dues)");
        System.out.println("Enter \t-> To check dues of All Users (have Cleared all Dues)");

        System.out.println("Enter \t-> To check status of a Book Loan");
        System.out.println("Enter \t-> To check status of all Book Loans");

        System.out.println("Enter \t-> To list all borrowed books by a user");
        System.out.println("Enter \t-> To list all borrowed books");

        System.out.println("Enter \t-> To list all reserved books by a user");
        System.out.println("Enter \t-> To list all reserved books by all users");

        System.out.println("Enter \t-> To list all returned books by a user");
        System.out.println("Enter \t-> To list all returned books");

        // ---------------------------------
        // Actions that a Librarian can perform on a Book Item
        System.out.println("Enter \t-> Add a BookItem (Book Metadata Already Exist)");
        System.out.println("Enter \t-> Add a BookItem (Book Metadata Doesn't Exist)");
        System.out.println("Enter \t-> Update a BookItem");
        System.out.println("Enter \t-> Remove a BookItem");

        // Listing all the Book Copies.
        System.out.println("Enter \t-> List all BookItems");
        System.out.println("Enter \t-> List all BookItems belong to a Author");
        System.out.println("Enter \t-> List all BookItems falls under a Genre");
        System.out.println("Enter \t-> List all BookItems within a Shelf");
        System.out.println("Enter \t-> List all BookItems within a Section");

        // To list all copies belong to a Single Metadata
        System.out.println("Enter \t-> List all BookItems based on ISBN");
        System.out.println("Enter \t-> List all BookItems based on Book Title");

        // To check the status of a BookItem (This will show -> whether the book is Available, Loaned(Rented) or Reserved)
        System.out.println("Enter \t-> To check the status of BookItems based on Barcode");
        System.out.println("Enter \t-> To check the status of BookItems based on ISBN");
        System.out.println("Enter \t-> To check the status of BookItems based on Author");
        System.out.println("Enter \t-> To check the status of BookItems based on Genre");
        System.out.println("Enter \t-> To check the status of BookItems based on Shelf_id");
        System.out.println("Enter \t-> To check the status of BookItems based on Section");

        // ------------------
        // To Log Out the User
        System.out.println("\nEnter 100\t-> To log out");

        System.out.println("\n\n" + "*".repeat(50));
    }

    public static void memberOptionsList(){
        System.out.println("\n" + "*".repeat(50));

        // These will be accessible only to user.
        System.out.println("\n\nEnter 01\t-> To Show ALl books");
        System.out.println("Enter 02\t-> To search a book by ISBN");
        System.out.println("Enter 03\t-> To search a book by Author");
        System.out.println("Enter 04\t-> To search a book by Title");
        System.out.println("Enter 05\t-> To search a book by Genre");

        // To Log out
        System.out.println("\nEnter 100\t-> To log out");

        System.out.println("\n\n" + "*".repeat(50));
    }
}
