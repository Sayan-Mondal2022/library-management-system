package com.library.util;

import com.library.dao.BookDao;
import com.library.dao.UserDao;
import com.library.models.Book;
import com.library.models.User;

import java.util.List;

public class ConsoleView {
    public static BookDao book_dao = new BookDao();
    public static UserDao user_dao = new UserDao();

    public static void displayBooks(String query){
        System.out.println("\n" + "-".repeat(50));
        try{
            List<Book> bookList;

            if (query.equalsIgnoreCase("deleted"))
                bookList = book_dao.showALLBooks("deleted");
            else
                bookList = book_dao.showALLBooks("all");

            System.out.println("Books are:\n");
            for(Book book : bookList){
                System.out.println(book);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        System.out.println("\n" + "-".repeat(50));
    }


    public static void displayUsers(String user_type){
        System.out.println("\n" + "-".repeat(50));
        try{
            List<User> userList = user_dao.getUsers(user_type);

            System.out.println("Users are:\n");
            for(User user : userList){
                System.out.println(user);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        System.out.println("\n" + "-".repeat(50));
    }
}
