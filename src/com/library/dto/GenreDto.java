package com.library.dto;

import com.library.dao.AuthorDao;
import com.library.dao.GenreDao;
import com.library.models.Author;
import com.library.models.Genre;

import java.util.List;
import java.util.Scanner;

public class GenreDto {
    private Scanner sc = new Scanner(System.in);

    public Genre addNewGenre() throws RuntimeException{
        System.out.println("\nEnter the author details:");

        System.out.print("Enter the genre name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty())
            throw new RuntimeException("Name can't be empty!");

        Genre genre = new Genre();
        genre.setGenre_name(name);

        return genre;
    }

    public int getGenreId(){
        GenreDao genre_dao = new GenreDao();
        try {
            System.out.println("\nExisting Genres are:\n");
            List<Genre> genres =  genre_dao.getAllGenres();

            for (Genre genre: genres){
                System.out.println("\n");
                System.out.println("Genre Id: " + genre.getGenre_id());
                System.out.println("Genre name: " + genre.getGenre_name());
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\nChoose genre id from existing genres.\nGenre doesn't exist? add a new Genre (Enter yes to add)!");
        String user_choice = sc.nextLine();
        int genre_id;

        if (user_choice.equalsIgnoreCase("yes")) {
            try {
                Genre genre = addNewGenre();
                genre = genre_dao.addGenre(genre);

                genre_id = genre.getGenre_id();

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.print("Enter the genre id: ");
            genre_id = sc.nextInt();
        }

        return genre_id;
    }
}
