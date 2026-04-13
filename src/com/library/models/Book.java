package com.library.models;

public class Book {
    private String isbn, title;
    private int author_id, genre_id;
    private String author_name, genre_name;

    private Integer publication_year, pages;
    private String publisher, edition, language, description;


    // These are the basic details, must have in a Book Metadata
    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }


    public String getAuthor_name() {
        return author_name;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    // These are optional enabled when user wants to add they can add:
    public void setPublication_year(Integer publication_year) {
        this.publication_year = publication_year;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    // Getters:
    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public int getAuthor_id() {
        return this.author_id;
    }

    public int getGenre_id() {
        return this.genre_id;
    }

    public Integer getPublication_year() {
        return this.publication_year;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public String getEdition() {
        return this.edition;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLanguage() {
        return this.language;
    }

    public Integer getPages() {
        return this.pages;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }
}
