package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.Author;
import com.library.models.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {
    public Genre addGenre(Genre genre) throws RuntimeException {
        String sql = "INSERT INTO Genres (name) VALUE (?);";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, genre.getGenre_name());

            ps.executeUpdate();

            System.out.println("New Author has been added to the database");
            return getLastGenre();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Genre getLastGenre() throws RuntimeException {
        String sql = "SELECT * FROM Genres ORDER BY id DESC LIMIT 1;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();
            res.next();

            Genre genre = new Genre();
            genre.setGenre_id(res.getInt("id"));
            genre.setGenre_name(res.getString("name"));

            return genre;
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
            genre.setGenre_id(res.getInt("id"));
            genre.setGenre_name(res.getString("name"));

            return genre;
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

                genre.setGenre_id(res.getInt("id"));
                genre.setGenre_name(res.getString("name"));

                genres.add(genre);
            }

            return genres;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
