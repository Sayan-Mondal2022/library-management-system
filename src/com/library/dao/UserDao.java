package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


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


    public User getUser(String query_type, String query) {
        String sqlQuery = "SELECT * FROM Users WHERE email = ? LIMIT 1";

        if (query_type.equalsIgnoreCase("id"))
            sqlQuery = "SELECT * FROM Users WHERE user_id = ? LIMIT 1";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlQuery)) {

            if (query_type.equalsIgnoreCase("id"))
                ps.setInt(1, Integer.parseInt(query));
            else
                ps.setString(1, query);

            ResultSet res = ps.executeQuery();
            if (!res.next()) {
                throw new RuntimeException("User not found");
            }

            return new User(
                    res.getInt("user_id"),
                    res.getString("user_name"),
                    res.getString("address"),
                    res.getString("phone_no"),
                    res.getString("email"),
                    res.getString("password_hash"),
                    res.getString("user_type")
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getUsers(String user_type) throws Exception {
        String sqlQuery;

        if (user_type.equalsIgnoreCase("allusers"))
            sqlQuery = "SELECT * FROM Users";
        else
            sqlQuery = "SELECT * FROM Users WHERE user_type=?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlQuery)) {
            List<User> userList = new ArrayList<>();

            if (user_type.equalsIgnoreCase("member") || user_type.equalsIgnoreCase("librarian"))
                ps.setString(1, user_type);

            ResultSet res = ps.executeQuery();

            // user_name, address, phone_no, email, password_hash, user_type
            while (res.next()) {
                userList.add(new User(
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getString("address"),
                        res.getString("phone_no"),
                        res.getString("email"),
                        res.getString("password_hash"),
                        res.getString("user_type")
                ));
            }

            return userList;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
