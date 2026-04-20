package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.BorrowBookDto;
import com.library.dto.FinedDetailsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FineDao {
    private BorrowBookDto setOverdueDetails(ResultSet res) throws SQLException {
        BorrowBookDto dto = new BorrowBookDto();

        dto.setBorrowId(res.getInt("borrow_id"));
        dto.setUserId(res.getInt("user_id"));
        dto.setBarcode(res.getString("barcode"));
        dto.setDueDays(res.getInt("day_diff"));

        return dto;
    }

    private FinedDetailsDto setFinedDetails(ResultSet res) throws SQLException {
        FinedDetailsDto dto = new FinedDetailsDto();

        dto.setFineId(res.getInt("fine_id"));
        dto.setBorrowId(res.getInt("borrow_id"));
        dto.setUserId(res.getInt("user_id"));
        dto.setBarcode(res.getString("barcode"));
        dto.setDueDays(res.getInt("due_days"));
        dto.setFineAmount(res.getDouble("fine_amount"));
        dto.setPaidAmount(res.getDouble("paid_amount"));
        dto.setIsPaid(res.getBoolean("is_paid"));
        dto.setReturnDate(res.getTimestamp("return_date") != null
                ? res.getTimestamp("return_date").toLocalDateTime()
                : null);

        return dto;
    }


    public ArrayList<BorrowBookDto> getOverdueUser(int userId) throws SQLException {
        String sql = """
                    SELECT
                        borrow_id,
                        user_id,
                        barcode,
                        TIMESTAMPDIFF(DAY, due_date, CURRENT_TIMESTAMP) AS day_diff
                    FROM BorrowedBooks
                    WHERE user_id = ?
                    HAVING day_diff > 0
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    responseList.add(setOverdueDetails(res));
                }
            }

            return responseList;
        }
    }

    public ArrayList<BorrowBookDto> getAllOverdueUsers() throws SQLException {
        String sql = """
                SELECT
                    borrow_id,
                    user_id,
                    barcode,
                    TIMESTAMPDIFF(DAY, due_date, CURRENT_TIMESTAMP) AS day_diff
                FROM BorrowedBooks
                HAVING day_diff > 0
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    responseList.add(setOverdueDetails(res));
                }
            }

            return responseList;
        }
    }

    public void fineAllUsers(ArrayList<FinedDetailsDto> finedUsers) throws SQLException {
        String sql = "INSERT INTO Fines (borrow_id, fine_amount) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                for (FinedDetailsDto dto : finedUsers) {
                    ps.setInt(1, dto.getBorrowId());
                    ps.setDouble(2, dto.getFineAmount());
                    ps.addBatch();
                }
                ps.executeBatch();
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Batch update failed, transaction rolled back", e);
            }
        }
    }


    public ArrayList<FinedDetailsDto> getAllFinedUsers() throws SQLException {
        String sql = """
                    SELECT
                        t1.fine_id,
                        t1.borrow_id,
                        t2.user_id,
                        t2.barcode,
                        TIMESTAMPDIFF(DAY, t2.issue_date, t2.due_date) AS due_days,
                        t1.fine_amount,
                        t1.paid_amount,
                        t1.is_paid,
                        t2.return_date
                    FROM Fines t1
                    INNER JOIN BorrowedBooks t2 ON t1.borrow_id = t2.borrow_id
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ArrayList<FinedDetailsDto> list = new ArrayList<>();

            try(ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    list.add(setFinedDetails(res));
                }
            }
            return list;
        }
    }

    public ArrayList<FinedDetailsDto> getFinedUser(int userId) throws SQLException {
        String sql = """
                    SELECT
                        t1.fine_id,
                        t1.borrow_id,
                        t2.user_id,
                        t2.barcode,
                        TIMESTAMPDIFF(DAY, t2.issue_date, t2.due_date) AS due_days,
                        t1.fine_amount,
                        t1.paid_amount,
                        t1.is_paid,
                        t2.return_date
                    FROM Fines t1
                    INNER JOIN BorrowedBooks t2
                        ON t1.borrow_id = t2.borrow_id
                    WHERE t2.user_id = ?
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);

            ArrayList<FinedDetailsDto> list = new ArrayList<>();

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    list.add(setFinedDetails(res));
                }
            }
            return list;
        }
    }


    public int getDueDays(int borrowId) throws SQLException {
        String sql = """
                SELECT
                	GREATEST(0, DATEDIFF(CURRENT_DATE, due_date)) AS due_days
                FROM BorrowedBooks
                WHERE borrow_id = ?;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, borrowId);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return res.getInt("due_days");
                } else {
                    throw new IllegalArgumentException("Invalid Borrow ID: No book found." + borrowId);
                }
            }
        }
    }

    public boolean checkFine(int borrowId) throws SQLException {
        String sql = "SELECT is_paid FROM fines WHERE borrow_id = ? ;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, borrowId);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return res.getBoolean("is_paid");
                } else {
                    return false;
                }
            } catch (SQLException e) {
                throw new SQLException("Database error while retrieving data", e);
            }
        }
    }

    public void collectFine(int borrowId) throws SQLException {
        String sql = """
                UPDATE Fines SET
                	paid_amount = fine_amount,
                    is_paid = TRUE,
                    paid_at = CURRENT_TIMESTAMP
                WHERE borrow_id = ?;
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, borrowId);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0)
                    throw new SQLException("Failed to update fines, no rows affected");
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed, while updating fines", e);
            }
        }
    }

    public void collectFine(int borrowId, double fineAmount) throws SQLException {
        String sql = """
                INSERT INTO Fines
                	(borrow_id, fine_amount, paid_amount, is_paid, paid_at)
                VALUES
                	(?, ?, ?, TRUE, CURRENT_TIMESTAMP)
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, borrowId);
                ps.setDouble(2, fineAmount);
                ps.setDouble(3, fineAmount);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0)
                    throw new SQLException("Failed to update fines, no rows affected");
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed, while updating fines", e);
            }
        }
    }
}
