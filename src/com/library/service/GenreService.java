package com.library.service;

import com.library.dao.GenreDao;
import com.library.dto.GenreDto;
import com.library.models.Genre;

import java.sql.SQLException;
import java.util.List;

public class GenreService {
    private final GenreDao dao = new GenreDao();

    public List<GenreDto> getAllGenres() throws SQLException {
        return dao.getAllGenres();
    }

    public int addGenre(GenreDto dto) throws SQLException {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new RuntimeException("Genre name cannot be empty");
        }

        Genre genre = new Genre();
        genre.setGenreName(dto.getName());

        return dao.addGenre(genre);
    }

    public GenreDto getGenre(int id) throws SQLException{
        return dao.getGenreDetails(id);
    }
}