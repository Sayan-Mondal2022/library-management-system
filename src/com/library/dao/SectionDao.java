package com.library.dao;

import com.library.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionDao {
    public int getOrCreateSection(String section_name) throws SQLException {
        String selectSql = "SELECT id, name FROM Section WHERE name = ?";
        String insertSql = "INSERT INTO Section (name) VALUES (?)";

        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(selectSql)) {
                ps.setString(1, section_name);
                try (ResultSet res = ps.executeQuery()) {
                    if (res.next()) {
                        return res.getInt("id");
                    }
                }
            }

            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, section_name);

                int rowAffected = ps.executeUpdate();
                if (rowAffected == 0) {
                    throw new SQLException("Insertion failed, no rows affected.");
                }

                try (ResultSet res = ps.getGeneratedKeys()) {
                    if (res.next()) {
                        con.commit();
                        return res.getInt("id");
                    } else {
                        throw new SQLException("Failed to retrieve generated keys.");
                    }

                } catch (SQLException e) {
                    con.rollback();
                    throw new SQLException("Transaction failed during Section creation", e);
                }
            }
        }
    }


    public List<String> getAllSectionNames() throws SQLException {
        String sql = "SELECT name FROM Section";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            List<String> names = new ArrayList<>();

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    names.add(res.getString("name"));
                }
            } catch (SQLException e) {
                throw new SQLException("Failed to retrieve the Section Names", e);
            }
            return names;
        }
    }
}
