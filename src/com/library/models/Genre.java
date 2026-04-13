package com.library.models;

public class Genre {
    private int genre_id;
    private String genre_name;

    // Setters
    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    // Getters
    public int getGenre_id() {
        return genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }
}
