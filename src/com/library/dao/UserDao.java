package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.UserDto;
import com.library.dto.UserSummaryDto;
import com.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao {
    private UserDto setUserDetails(ResultSet res) throws SQLException {
        UserDto user = new UserDto();

        user.setUserId(res.getInt("user_id"));
        user.setUserName(res.getString("username"));
        user.setAddress(res.getString("address"));
        user.setPhoneNo(res.getString("phone_no"));
        user.setEmail(res.getString("email"));
        user.setPasswordHash(res.getString("password_hash"));
        user.setUserType(res.getString("user_type"));

        return user;
    }

    public UserDto save(UserDto user) throws SQLException {
        String sql = """
                INSERT INTO Users (username, email, phone_no, address, password_hash, user_type)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPhoneNo());
                ps.setString(4, user.getAddress());
                ps.setString(5, user.getPasswordHash());
                ps.setString(6, user.getUserType().toUpperCase());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Failed to insert user, no rows affected.");
                }

                try (ResultSet res = ps.getGeneratedKeys()) {
                    if (res.next()) {
                        con.commit();
                        user.setUserId(res.getInt(1));

                        return user;
                    } else {
                        throw new SQLException("Failed to retrieve generated ID.");
                    }
                }
            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed, while inserting User details", e);
            }
        }
    }


    public void updateUserDetails(User user) throws SQLException {
        String sql = """
                UPDATE Users SET
                    username = ?,
                    email = ?,
                    phone_no = ?,
                    address = ?,
                    password_hash = ?
                WHERE user_id = ?;
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPhoneNo());
                ps.setString(4, user.getAddress());
                ps.setString(5, user.getPasswordHash());

                ps.setInt(6, user.getUserId());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    con.commit();
                } else {
                    throw new SQLException("Failed to update user details, no rows affected.");
                }
            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction Failed while updating User Details", e);
            }
        }
    }


    public UserDto getUser(String queryType, String query) throws SQLException {
        String sqlQuery = "SELECT * FROM Users WHERE email = ? LIMIT 1";

        if (queryType.equalsIgnoreCase("id"))
            sqlQuery = "SELECT * FROM Users WHERE user_id = ? LIMIT 1";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlQuery)) {

            if (queryType.equalsIgnoreCase("id"))
                ps.setInt(1, Integer.parseInt(query));
            else
                ps.setString(1, query);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return setUserDetails(res);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new SQLException("Database error while retrieving user", e);
            }
        }
    }


    public List<UserDto> getAllUsers(String userType) throws SQLException {
        String sqlQuery = "SELECT * FROM Users WHERE user_type=?";

        if (userType.equalsIgnoreCase("allusers"))
            sqlQuery = "SELECT * FROM Users";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlQuery)) {
            if (!userType.equalsIgnoreCase("allusers"))
                ps.setString(1, userType.toUpperCase());

            List<UserDto> userList = new ArrayList<>();

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    userList.add(setUserDetails(res));
                }
            } catch (SQLException e) {
                throw new SQLException("Failed to retrieve User Details", e);
            }
            return userList;
        }
    }


    public UserSummaryDto getUserSummary(int userId) throws SQLException {
        String sql = """
                SELECT
                	t1.user_id,
                    t1.username,
                    t1.email,
                    t1.phone_no,
                    t1.address,
                    t1.user_type,
                
                    COUNT(DISTINCT t2.borrow_id) AS total_borrowed_books,
                    SUM(
                        CASE
                            WHEN t2.return_date IS NOT NULL THEN 1
                            ELSE 0
                        END
                    ) AS total_returned_books,
                
                    COALESCE(SUM(t3.fine_amount), 0) AS total_fine,
                    COALESCE(SUM(t3.paid_amount), 0) AS total_paid,
                
                    t1.is_blacklisted,
                    COALESCE(t1.blacklist_reason, "N/A") AS blacklist_reason
                
                FROM Users AS t1 LEFT JOIN BorrowedBooks AS t2 ON t1.user_id = t2.user_id
                LEFT JOIN Fines AS t3 ON t2.borrow_id = t3.borrow_id
                WHERE t1.user_id = ?
                GROUP BY t1.user_id;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserSummaryDto dto = new UserSummaryDto();

                    dto.setUserId(rs.getInt("user_id"));
                    dto.setUserName(rs.getString("username"));
                    dto.setEmail(rs.getString("email"));
                    dto.setPhoneNo(rs.getString("phone_no"));
                    dto.setAddress(rs.getString("address"));
                    dto.setUserType(rs.getString("user_type"));

                    dto.setTotalBorrowedBooks(rs.getInt("total_borrowed_books"));
                    dto.setTotalReturnedBooks(rs.getInt("total_returned_books"));

                    dto.setTotalFine(rs.getDouble("total_fine"));
                    dto.setTotalPaid(rs.getDouble("total_paid"));

                    dto.setBlacklisted(rs.getBoolean("is_blacklisted"));
                    dto.setBlacklistReason(rs.getString("blacklist_reason"));

                    return dto;
                }
                return null;
            }
        }
    }


    public List<UserDto> fetchNonBlacklistedUsers() throws SQLException{
        String sql = """
                SELECT user_id, username
                FROM Users
                WHERE is_blacklisted = FALSE;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            List<UserDto> userList = new ArrayList<>();

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    UserDto dto = new UserDto();

                    dto.setUserId(res.getInt("user_id"));
                    dto.setUserName(res.getString("username"));

                    userList.add(dto);
                }
            } catch (SQLException e) {
                throw new SQLException("Failed to retrieve User Details", e);
            }
            return userList;
        }
    }

    public void blacklistUser(int userId, String reason) throws SQLException {
        String sql = """
                UPDATE Users SET
                is_blacklisted = TRUE,
                blacklist_reason = ?,
                blacklisted_at = CURRENT_TIMESTAMP
                WHERE user_id = ?
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, reason);
                ps.setInt(2, userId);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Failed to update user, no rows affected.");
                }
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction Failed while updating User Details", e);
            }

        }
    }
}
