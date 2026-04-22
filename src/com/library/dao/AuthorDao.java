package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.AuthorDto;
import com.library.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    public int addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO Authors (name, nationality) VALUES (?, ?);";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, author.getAuthorName());
                ps.setString(2, author.getNationality());
                int rows = ps.executeUpdate();

                if (rows == 0) {
                    throw new RuntimeException("Failed to insert author");
                }

                try (ResultSet res = ps.getGeneratedKeys()) {
                    if (res.next()) {
                        con.commit();
                        return res.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated ID.");
                    }
                }
            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed during author insertion", e);
            }
        }
    }


    private AuthorDto setAuthorDetails(ResultSet res) throws SQLException {
        AuthorDto author = new AuthorDto();

        author.setId(res.getInt("id"));
        author.setName(res.getString("name"));
        author.setNationality(res.getString("nationality"));

        return author;
    }

    public AuthorDto getAuthorDetails(int author_id) throws SQLException {
        String sql = "SELECT * FROM Authors WHERE id = ? ORDER BY id ASC;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, author_id);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return setAuthorDetails(res);
                } else {
                    return null;
                }
            }
        }
    }

    public List<String> getAllAuthorNames() throws SQLException {
        String sql = "SELECT name FROM Authors ORDER BY id ASC";
        List<String> names = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet res = ps.executeQuery()) {
            while (res.next()) {
                names.add(res.getString("name"));
            }
        }
        return names;
    }


    public List<AuthorDto> getAllAuthors() throws SQLException {
        String sql = "SELECT * FROM Authors ORDER BY id ASC";
        List<AuthorDto> authors = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet res = ps.executeQuery()) {
            while (res.next()) {
                authors.add(setAuthorDetails(res));
            }
        }
        return authors;
    }
}
