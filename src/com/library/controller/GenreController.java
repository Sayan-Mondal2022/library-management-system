package com.library.controller;

import com.library.dto.GenreDto;
import com.library.models.Genre;
import com.library.service.GenreService;
import com.library.util.Validators;

import java.util.List;
import java.util.Scanner;

public class GenreController {
    private final Scanner sc = new Scanner(System.in);
    private final GenreService service = new GenreService();

    public void displayGenres() throws RuntimeException {
        try {
            List<Genre> genres = service.getAllGenres();

            System.out.println("\nAvailable Genres:");
            for (Genre g : genres) {
                System.out.println("Id: " + g.getGenreId() + ", name: " + g.getGenreName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int selectGenre() {
        try {
            displayGenres();
            List<Genre> genres = service.getAllGenres();

            System.out.print("Add new genre? (yes/no): ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {

                GenreDto dto = new GenreDto();

                System.out.print("Name: ");
                dto.setName(sc.nextLine());

                System.out.println("New Genre has been added to the database");
                return service.addGenre(dto);
            }

            while (true) {
                int id = Validators.readInt("Enter Genre ID: ");

                boolean exists = genres.stream()
                        .anyMatch(a -> a.getGenreId() == id);

                if (!exists) {
                    System.out.println("Invalid Genre ID! Choose from the list.");
                    continue;
                }

                return id;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}