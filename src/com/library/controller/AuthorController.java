package com.library.controller;

import com.library.dto.AuthorDto;
import com.library.models.Author;
import com.library.service.AuthorService;
import com.library.util.Validators;

import java.util.List;
import java.util.Scanner;

public class AuthorController {
    private final Scanner sc = new Scanner(System.in);
    private final AuthorService service = new AuthorService();

    public void displayAuthors() throws RuntimeException{
        try {
            List<Author> authors = service.getAllAuthors();

            System.out.println("\nAvailable Authors:");
            for (Author a : authors) {
                System.out.println("Id: " + a.getAuthorId() + ", name:  " + a.getAuthorName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int selectAuthor() {
        try {
            displayAuthors();
            List<Author> authors = service.getAllAuthors();

            System.out.print("\nAdd new author? (yes/no): ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {

                AuthorDto dto = new AuthorDto();

                System.out.print("Name: ");
                dto.setName(sc.nextLine());

                System.out.print("Nationality: ");
                dto.setNationality(sc.nextLine());

                System.out.println("New Author has been added to the database");
                return service.addAuthor(dto);
            }

            while (true) {
                int id = Validators.readInt("Enter Author ID: ");

                boolean exists = authors.stream()
                        .anyMatch(a -> a.getAuthorId() == id);

                if (!exists) {
                    System.out.println("Invalid Author ID! Choose from the list.");
                    continue;
                }
                return id;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}