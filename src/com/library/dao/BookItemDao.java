package com.library.dao;

public class BookItemDao {
    // Accessible to all
    public void ShowBooks() {
        String sql = "SELECT * FROM BookItems";
    }

    public void ShowBooks(int isbn) {
        String sql = "SELECT * FROM BookItems WHERE isbn = ?";
    }

    public void ShowBooks(String status) {
        String sql = "SELECT * FROM BookItems WHERE status = ?";
    }

    public void ShowBooks(int isbn, String status) {
        String sql = "SELECT * FROM BookItems WHERE isbn = ? AND status = ?";
    }

    public void showBooksInShelf(int shelf_id) {
        String sql = "SELECT * FROM BookItems WHERE shelf_id = ?";
    }

    // Only accessible to librarian
    public void librarianAddBook(){
        // Will add extra books to the library with a existing isbn.
        // If a ISBN doesn't exist create one.
        // If a Shelf doesn't exist create one.
    }

    public void librarianMoveBook(){
        // This function will move a particular set of books from one shelf to another.
        // From one Section to Another.
    }

    public void librarianRemoveBook(){
        // This function will remove only a book with a Barcode.
        // If removed change the bookItem status to removed=True.
    }
}
