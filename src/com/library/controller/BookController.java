package com.library.controller;

import com.library.dto.BookResponseDto;
import com.library.service.BookService;

import java.util.List;

public class BookController {
    private BookService service = new BookService();

    public void showBookIsbnTitle() {
        try {
            List<BookResponseDto> books = service.getALlBookIsbn_Title();

            for(BookResponseDto book: books){
                System.out.println("\n");
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("Book title: " + book.getTitle());
            }

        } catch (RuntimeException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
