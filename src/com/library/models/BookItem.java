package com.library.models;

import com.library.enums.BookCondition;
import com.library.enums.BookStatus;

public class BookItem {
    private String barcode, isbn;
    private String shelf_id, section;
    private boolean is_removed;
    private BookStatus book_status;
    private BookCondition book_condition;

    public String getBarcode() {
        return barcode;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookStatus getBook_status() {
        return book_status;
    }

    public BookCondition getBook_condition() {
        return book_condition;
    }

    public String getShelf_id() {
        return shelf_id;
    }

    public String getSection() {
        return section;
    }

    public boolean isIs_removed() {
        return is_removed;
    }

    // Setters
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setBook_condition(BookCondition book_condition) {
        this.book_condition = book_condition;
    }

    public void setBook_status(BookStatus book_status) {
        this.book_status = book_status;
    }

    public void setShelf_id(String shelf_id) {
        this.shelf_id = shelf_id;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setIs_removed(boolean is_removed) {
        this.is_removed = is_removed;
    }
}
