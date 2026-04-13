package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    public Author addAuthor(Author author) throws RuntimeException {
        String sql = "INSERT INTO Authors (name, nationality) VALUES (?, ?);";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, author.getAuthor_name());
            ps.setString(2, author.getNationality());
            ps.executeUpdate();

            System.out.println("New Author has been added to the database");
            return getLastAuthor();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Author getLastAuthor() throws RuntimeException {
        String sql = "SELECT * FROM Authors ORDER BY id DESC LIMIT 1;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();
            res.next();

            Author author = new Author();
            author.setAuthor_id(res.getInt("id"));
            author.setAuthor_name(res.getString("name"));
            author.setNationality(res.getString("nationality"));

            return author;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Author getAuthorDetails(int author_id) throws RuntimeException {
        String sql = "SELECT * FROM Authors WHERE id = ?;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, author_id);
            ResultSet res = ps.executeQuery();
            res.next();

            Author author = new Author();
            author.setAuthor_id(res.getInt("id"));
            author.setAuthor_name(res.getString("name"));
            author.setNationality(res.getString("nationality"));

            return author;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Author> getAllAuthors() throws RuntimeException {
        String sql = "SELECT * FROM Authors";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            List<Author> authors = new ArrayList<>();

            while (res.next()) {
                Author author = new Author();

                author.setAuthor_id(res.getInt("id"));
                author.setAuthor_name(res.getString("name"));
                author.setNationality(res.getString("nationality"));

                authors.add(author);
            }

            return authors;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
