package com.library;

import com.library.models.Book;
import com.library.dao.BookDao;

public class Main {
    public static void main(String[] args) {
        // 1. Create the data (Model)
        Book myBook = new Book(12345, "Java Programming", "Sayan", "Coding");

        // 2. Use the worker (DAO) to save it
        BookDao dao = new BookDao();
        dao.insertBook(myBook);
    }
}