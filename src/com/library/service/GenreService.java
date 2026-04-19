package com.library.service;

import com.library.dao.GenreDao;
import com.library.dto.GenreDto;
import com.library.models.Genre;

import java.util.List;

public class GenreService {
    private final GenreDao dao = new GenreDao();

    public List<Genre> getAllGenres() {
        return dao.getAllGenres();
    }

    public int addGenre(GenreDto dto) throws RuntimeException {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new RuntimeException("Genre name cannot be empty");
        }

        Genre genre = new Genre();
        genre.setGenreName(dto.getName());

        return dao.addGenre(genre);
    }

    public Genre getGenre(int id) {
        return dao.getGenreDetails(id);
    }
}