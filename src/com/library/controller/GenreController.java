package com.library.controller;

import com.library.dto.GenreDto;
import com.library.models.Genre;
import com.library.service.GenreService;
import com.library.util.Validators;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GenreController {
    private final Scanner sc = new Scanner(System.in);
    private final GenreService service = new GenreService();

    public void displayGenres() throws SQLException {
        List<GenreDto> genres = service.getAllGenres();

        System.out.println("\n" + "-".repeat(20) + " GENRES ARE " + "-".repeat(20));
        for (GenreDto g : genres) {
            System.out.println("Id: " + g.getId() + ", name: " + g.getName());
        }
        System.out.println("-".repeat(16) + " END OF GENRES LIST " + "-".repeat(16));
    }

    public int selectGenre() {
        try {
            displayGenres();

            System.out.print("\nAdd new genre? (yes/no): ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {

                GenreDto dto = new GenreDto();

                System.out.print("Enter Genre name: ");
                dto.setName(sc.nextLine());

                System.out.println("New Genre has been added to the database");
                return service.addGenre(dto);
            }

            return getValidGenreId();
        } catch (SQLException | RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return -1;
    }


    private int getValidGenreId() throws SQLException {
        List<GenreDto> genres = service.getAllGenres();

        while (true) {
            int id = Validators.getValidInt("Enter Genre ID: ");

            boolean exists = genres.stream()
                    .anyMatch(a -> a.getId() == id);

            if (!exists) {
                System.out.println("Invalid Genre ID! Choose from the list.");
                continue;
            }

            return id;
        }
    }
}