package com.library.models;

public class Author {
    private int authorId;
    private String authorName, nationality;


    // Getters:
    public int getAuthorId() {
        return this.authorId;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public String getNationality() {
        return this.nationality;
    }


    public void setAuthorId(int author_id) {
        this.authorId = author_id;
    }

    public void setAuthorName(String author_name) {
        this.authorName = author_name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
