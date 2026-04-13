package com.library.models;

public class Author {
    private int author_id;
    private String author_name, nationality;


    // Getters:
    public int getAuthor_id() {
        return this.author_id;
    }

    public String getAuthor_name() {
        return this.author_name;
    }

    public String getNationality() {
        return this.nationality;
    }


    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
