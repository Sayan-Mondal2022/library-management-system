package com.library.service;

import com.library.dao.AuthorDao;
import com.library.dto.AuthorDto;
import com.library.models.Author;

import java.sql.SQLException;
import java.util.List;

public class AuthorService {
    private final AuthorDao dao = new AuthorDao();

    public List<AuthorDto> getAllAuthors() throws SQLException {
        return dao.getAllAuthors();
    }

    public int addAuthor(AuthorDto dto) throws SQLException {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new RuntimeException("Author name cannot be empty");
        }

        Author author = new Author();
        author.setAuthorName(dto.getName());
        author.setNationality(dto.getNationality());

        return dao.addAuthor(author);
    }

    public AuthorDto getAuthor(int id) throws SQLException{
        return dao.getAuthorDetails(id);
    }
}
