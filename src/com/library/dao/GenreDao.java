package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.GenreDto;
import com.library.models.Author;
import com.library.models.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {
    public int addGenre(Genre genre) throws SQLException {
        String sql = "INSERT INTO Genres (name) VALUE (?);";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, genre.getGenreName());
                int rows = ps.executeUpdate();

                if (rows == 0)
                    throw new RuntimeException("Failed to insert Genre");


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
                throw new SQLException("Transaction failed during genre insertion", e);
            }
        }
    }


    private GenreDto setGenreDetails(ResultSet res) throws SQLException {
        GenreDto genre = new GenreDto();

        genre.setId(res.getInt("id"));
        genre.setName(res.getString("name"));

        return genre;
    }

    public GenreDto getGenreDetails(int genre_id) throws SQLException {
        String sql = "SELECT * FROM Genres WHERE id = ?;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, genre_id);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next())
                    return setGenreDetails(res);
                return null;
            }
        }
    }


    public List<String> getAllGenreNames() throws SQLException {
        String sql = "SELECT name FROM Genres";
        List<String> names = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet res = ps.executeQuery()) {
            while (res.next()) {
                names.add(res.getString("name"));
            }
        }
        return names;
    }


    public List<GenreDto> getAllGenres() throws SQLException {
        String sql = "SELECT * FROM Genres";
        List<GenreDto> genres = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet res = ps.executeQuery();) {
            while (res.next()) {
                genres.add(setGenreDetails(res));
            }
        }
        return genres;
    }

}
