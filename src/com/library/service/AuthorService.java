package com.library.service;

import com.library.dao.AuthorDao;
import com.library.dto.AuthorDto;
import com.library.models.Author;

import java.util.List;

public class AuthorService {
    private final AuthorDao dao = new AuthorDao();

    public List<Author> getAllAuthors() {
        return dao.getAllAuthors();
    }

    public Author addAuthor(AuthorDto dto) throws RuntimeException {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new RuntimeException("Author name cannot be empty");
        }

        Author author = new Author();
        author.setAuthorName(dto.getName());
        author.setNationality(dto.getNationality());

        return dao.addAuthor(author);
    }

    public Author getAuthor(int id) {
        return dao.getAuthorDetails(id);
    }
}
