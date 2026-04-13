package com.library.service;

import com.library.dao.BookDao;
import com.library.dto.BookResponseDto;

import java.util.List;

public class BookService {
    private BookDao dao = new BookDao();

    public List<BookResponseDto> getALlBookIsbn_Title() throws RuntimeException {
            return dao.showBookIsbnTitle(false);

    }
}
