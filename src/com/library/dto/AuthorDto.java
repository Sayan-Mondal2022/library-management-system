package com.library.dto;

import com.library.dao.AuthorDao;
import com.library.models.Author;

import java.util.List;
import java.util.Scanner;

public class AuthorDto {
    private Scanner sc = new Scanner(System.in);

    public Author addNewAuthor() throws RuntimeException{
        System.out.println("\nEnter the author details:");

        System.out.print("Enter the author name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter the author Nationality: ");
        String nationality = sc.nextLine().trim();

        if (name.isEmpty() || nationality.isEmpty())
            throw new RuntimeException("Name and Nationality can't be empty!");


        Author author = new Author();
        author.setAuthor_name(name);
        author.setNationality(nationality);

        return author;
    }

    public int getAuthorId(){
        AuthorDao author_dao = new AuthorDao();
        try {
            System.out.println("\nExisting Authors are:\n");
            List<Author> authors = author_dao.getAllAuthors();

            for (Author author : authors){
                System.out.println("\n");
                System.out.println("Author Id: " + author.getAuthor_id());
                System.out.println("Author name: " + author.getAuthor_name());
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\nChoose author id from the existing author.\nAuthor doesn't exit? add a new Author (Enter yes to add)!");
        String user_choice = sc.nextLine();
        int author_id;

        if (user_choice.equalsIgnoreCase("yes")){
            try {
                Author author = addNewAuthor();
                author = author_dao.addAuthor(author);

                author_id = author.getAuthor_id();

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.print("Enter the author id: ");
            author_id = sc.nextInt();
        }
        return author_id;
    }
}
