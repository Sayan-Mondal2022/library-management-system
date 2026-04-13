package com.library.dao;

import com.library.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookSectionDao {
    public int getOrCreateSection(String section_name) throws RuntimeException {
        String selectSql = "SELECT id, name FROM Section WHERE name = ?";
        String insertSql = "INSERT INTO Section (name) VALUES (?)";


        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(selectSql)) {
                ps.setString(1, section_name);
                ResultSet res = ps.executeQuery();

                if (res.next()) {
                    return res.getInt("id");
                }
            }
            try (PreparedStatement ps = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, section_name);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new RuntimeException("Failed to get or create section");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
