package com.library.dao;

import com.library.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShelvesDao {
    public void addShelfId(String shelf_id, int section_id) throws SQLException {
        String getSql = "SELECT * FROM Shelves WHERE id = ?";
        String insertSql = "INSERT INTO Shelves (id, section_id) VALUES (?, ?);";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement(getSql)) {
                ps.setString(1, shelf_id);

                try(ResultSet res = ps.executeQuery()){
                    if (res.next())
                        return;
                }
            }

            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, shelf_id);
                ps.setInt(2, section_id);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Failed to insert shelf, no rows affected.");
                }

                con.commit();

            } catch (SQLException e){
                con.rollback();
                throw new SQLException("Transaction Failed while adding the Data into Shelves table", e);
            }
        }
    }
}
