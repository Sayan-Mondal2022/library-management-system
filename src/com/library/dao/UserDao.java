package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class UserDao {
    public int save(User user) {
        Connection con = null;
        int user_id = -1;

        try {
            // Have the DB connection established.
            con = DBConnection.getConnection();

            String fetchEmailQuery = "SELECT email FROM Users WHERE email = ? LIMIT = 1";
            String fetchPhoneNoQuery = "SELECT email FROM Users WHERE phone_no = ? LIMIT = 1";






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

        return user_id;
    }
}
