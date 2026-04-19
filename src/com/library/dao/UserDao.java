package com.library.dao;

import com.library.db.DBConnection;
import com.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao {
    private User setUserDetails(ResultSet res) throws SQLException {
        User user = new User();

        user.setUserId(res.getInt("user_id"));
        user.setUserName(res.getString("username"));
        user.setAddress(res.getString("address"));
        user.setPhoneNo(res.getString("phone_no"));
        user.setEmail(res.getString("email"));
        user.setPasswordHash(res.getString("password_hash"));
        user.setUserType(res.getString("user_type"));

        return user;
    }

    public void save(User user) throws SQLException {
        String sql = """
                INSERT INTO Users (username, email, phone_no, address, password_hash, user_type)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNo());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getUserType().toUpperCase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
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
            return setUserDetails(res);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getUsers(String user_type) throws SQLException {
        String sqlQuery = "SELECT * FROM Users WHERE user_type=?";

        if (user_type.equalsIgnoreCase("allusers"))
            sqlQuery = "SELECT * FROM Users";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlQuery)) {
            List<User> userList = new ArrayList<>();

            if (user_type.equalsIgnoreCase("member") || user_type.equalsIgnoreCase("librarian"))
                ps.setString(1, user_type.toUpperCase());

            ResultSet res = ps.executeQuery();

            while (res.next()) {
                userList.add(setUserDetails(res));
            }
            return userList;

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
