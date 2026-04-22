package com.library.controller;

import com.library.dto.AuthorDto;
import com.library.models.Author;
import com.library.service.AuthorService;
import com.library.util.Validators;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AuthorController {
    private final Scanner sc = new Scanner(System.in);
    private final AuthorService service = new AuthorService();

    public void displayAuthors() throws SQLException {
        List<AuthorDto> authors = service.getAllAuthors();

        System.out.println("\n" + "-".repeat(20) + " AUTHORS ARE " + "-".repeat(20));
        for (AuthorDto a : authors) {
            System.out.println("Id: " + a.getId() + ", name:  " + a.getName());
        }
        System.out.println("-".repeat(16) + " END OF AUTHORS LIST " + "-".repeat(16));
    }

    public int selectAuthor() {
        try {
            displayAuthors();

            System.out.print("\nAdd new author? (yes/no): ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {

                AuthorDto dto = new AuthorDto();

                System.out.print("Enter Author name: ");
                dto.setName(sc.nextLine());

                System.out.print("Nationality: ");
                dto.setNationality(sc.nextLine());

                System.out.println("New Author has been added to the database");
                return service.addAuthor(dto);
            }
            return getValidAuthorId();

        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return 0;
    }

    private int getValidAuthorId() throws SQLException{
        List<AuthorDto> authors = service.getAllAuthors();

        while (true) {
            int id = Validators.getValidInt("Enter Author ID: ");

            boolean exists = authors.stream()
                    .anyMatch(a -> a.getId() == id);

            if (!exists) {
                System.out.println("Invalid Author ID! Choose from the list.");
                continue;
            }
            return id;
        }
    }

}