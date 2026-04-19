package com.library.dao;

import com.library.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShelvesDao {
    public void addShelfId(String shelf_id, int section_id) throws RuntimeException {
        String getSql = "SELECT * FROM Shelves WHERE id = ?";
        String insertSql = "INSERT INTO Shelves (id, section_id) VALUES (?, ?);";

        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(getSql)) {
                ps.setString(1, shelf_id);

                ResultSet res = ps.executeQuery();

                if (res.next())
                    return;
            }
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, shelf_id);
                ps.setInt(2, section_id);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0)
                    return;

                throw new RuntimeException("ERROR while adding the Data into Shelves table!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
