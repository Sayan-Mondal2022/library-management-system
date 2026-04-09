package com.library.models;

public class BookItem {
    private String barcode;
    private int isbn;
    private String shelf_id, section;
    private String status;     // This will be either Available, Loaned, Reserved
    private boolean removed;    // Will be false by default.

    public BookItem(String barcode, int isbn, String shelf_id, String section, String status, boolean removed){
        this.barcode = barcode;
        this.isbn = isbn;
        this.shelf_id = shelf_id;
        this.section = section;
        this.status = status;
        this.removed = removed;
    }

    // Getters
    public int getIsbn() {
        return this.isbn;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public String getShelf_id() {
        return this.shelf_id;
    }

    public String getSection() {
        return this.section;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isRemoved() {
        return this.removed;
    }
}
