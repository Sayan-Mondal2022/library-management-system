package com.library.dto;

import com.library.models.BookItem;
import com.library.util.MenuController;
import java.util.Scanner;

public class BookItemDto {
    public static  Scanner sc = new Scanner(System.in);

    public BookItem getBookItemDetails(int isbn) throws RuntimeException{
        System.out.println("\nEnter the BookItem details: ");
        String barcode, shelf_id, section;
        String status = "available";    // It is set to available while it is added for the first time.

        System.out.print("Enter the barcode: ");
        barcode = sc.nextLine();

        System.out.print("Enter the Shelf id: ");
        shelf_id = sc.nextLine();

        System.out.print("Enter the Book Section: ");
        section = sc.nextLine();

        if (barcode.isEmpty() || shelf_id.isEmpty() || section.isEmpty())
            throw new RuntimeException("Values can't be empty");
        if (barcode.length() < 10)
            throw new RuntimeException("Barcode can't be less than 10");

        return new BookItem(
                barcode,
                isbn,
                shelf_id,
                section,
                status
        );
    }
}
