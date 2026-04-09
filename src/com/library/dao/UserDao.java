package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDao {
    // This function is used to save the user data into my DB, Completing the user registration process
    public void save(User user) {
        Connection con = null;
        int user_id;

        try {
            // Have the DB connection established.
            con = DBConnection.getConnection();

            // Once I get the User_id I will insert the data.
            String sql = "INSERT INTO Users (user_name, address, phone_no, email, password_hash, user_type) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {

                pstmt.setString(1, user.getUserName());
                pstmt.setString(2, user.getAddress());
                pstmt.setString(3, user.getPhoneNo());
                pstmt.setString(4, user.getEmail());
                pstmt.setString(5, user.getPasswordHash());
                pstmt.setString(6, user.getUserType());

                pstmt.executeUpdate();

                System.out.println("User Data has been added to the Database!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to build the DB connection" + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    System.err.println("ERROR: Failed to close the DB connection.");
                }
            }
        }
    }
}
