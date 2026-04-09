package com.library.models;

public class Book {
    private String title, author, genre;
    private int isbn;

    public Book(int isbn, String title, String author, String genre){
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    // Getters to access the Private variables
    public int getIsbn() {
        return this.isbn;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getGenre(){
        return this.genre;
    }
}
