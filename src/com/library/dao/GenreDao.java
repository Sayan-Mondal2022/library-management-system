package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.Author;
import com.library.models.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {
    public int addGenre(Genre genre) throws RuntimeException {
        String sql = "INSERT INTO Genres (name) VALUE (?);";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, genre.getGenreName());
            int rows = ps.executeUpdate();

            if (rows == 0)
                throw new RuntimeException("Failed to insert Genre");


            ResultSet res = ps.getGeneratedKeys();
            if (!res.next())
                throw new RuntimeException("Failed to retrieve generated ID");

            return res.getInt("id");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Genre getGenreDetails(int genre_id) throws RuntimeException {
        String sql = "SELECT * FROM Genres WHERE id = ?;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, genre_id);
            ResultSet res = ps.executeQuery();
            res.next();

            Genre genre = new Genre();
            genre.setGenreId(res.getInt("id"));
            genre.setGenreName(res.getString("name"));

            return genre;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> getAllGenreNames() throws SQLException {
        String sql = "SELECT name FROM Genres";

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


    public List<Genre> getAllGenres() throws RuntimeException {
        String sql = "SELECT * FROM Genres";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            List<Genre> genres = new ArrayList<>();

            while (res.next()) {
                Genre genre = new Genre();

                genre.setGenreId(res.getInt("id"));
                genre.setGenreName(res.getString("name"));

                genres.add(genre);
            }

            return genres;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
