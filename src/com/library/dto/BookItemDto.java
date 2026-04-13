package com.library.dto;

import com.library.models.BookItem;
import com.library.enums.BookCondition;
import com.library.enums.BookStatus;

public class BookItemDto {
    private String barcode, isbn, shelf_id, section_name, status, condition;
    private boolean removed;
    private BookStatus book_status;
    private BookCondition book_condition;


    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public String getSection_name() {
        return this.section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public BookStatus getBook_status() {
        return this.book_status;
    }

    public BookCondition getBook_condition() {
        return this.book_condition;
    }

    public void setBook_condition(BookCondition book_condition) {
        this.book_condition = book_condition;
    }

    public void setBook_status(BookStatus book_status) {
        this.book_status = book_status;
    }


    public boolean getRemoved() {
        return this.removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public String getShelf_id() {
        return this.shelf_id;
    }

    public void setShelf_id(String shelf_id) {
        this.shelf_id = shelf_id;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
