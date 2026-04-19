package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    public int addAuthor(Author author) throws RuntimeException {
        String sql = "INSERT INTO Authors (name, nationality) VALUES (?, ?);";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getAuthorName());
            ps.setString(2, author.getNationality());
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Failed to insert author");
            }

            ResultSet res = ps.getGeneratedKeys();

            if (!res.next())
                throw new RuntimeException("Failed to retrieve generated ID");

            return res.getInt("id");

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
            author.setAuthorId(res.getInt("id"));
            author.setAuthorName(res.getString("name"));
            author.setNationality(res.getString("nationality"));

            return author;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllAuthorNames() throws SQLException {
        String sql = "SELECT name FROM Authors";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            List<String> names = new ArrayList<>();

            while (res.next()) {
                names.add(res.getString("name"));
            }
            return names;

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

                author.setAuthorId(res.getInt("id"));
                author.setAuthorName(res.getString("name"));
                author.setNationality(res.getString("nationality"));

                authors.add(author);
            }

            return authors;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
