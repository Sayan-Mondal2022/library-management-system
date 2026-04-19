package com.library.models;

public class Genre {
    private int genreId;
    private String genreName;

    // Setters
    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    // Getters
    public int getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }
}
