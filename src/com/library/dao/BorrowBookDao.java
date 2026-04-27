package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.*;
import com.library.enums.ApplicantStatus;
import com.library.enums.BookStatus;
import com.library.models.BorrowBook;

import java.sql.*;
import java.util.ArrayList;

public class BorrowBookDao {
    public ArrayList<ApplicantsDto> getPendingApplicants() throws SQLException {
        String sql = """
                SELECT
                    user_id,
                    barcode,
                    status
                FROM BorrowBookApplicants
                WHERE status = "PENDING";
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ArrayList<ApplicantsDto> applicantList = new ArrayList<>();

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    ApplicantsDto dto = new ApplicantsDto();

                    dto.setUserId(res.getInt("user_id"));
                    dto.setBarcode(res.getString("barcode"));
                    dto.setStatus(res.getString("status"));

                    applicantList.add(dto);
                }
            }

            return applicantList;
        }
    }

    public void issueBook(BorrowBook data) throws SQLException {
        String sql = "INSERT INTO BorrowedBooks (user_id, barcode, issue_date, due_date) VALUES (?, ?, ?, ?);";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, data.getUserId());
                ps.setString(2, data.getBarcode());
                ps.setTimestamp(3, Timestamp.valueOf(data.getIssueDate()));
                ps.setTimestamp(4, Timestamp.valueOf(data.getDueDate()));

                int res = ps.executeUpdate();

                if (res > 0) {
                    changeApplicantStatus(con, data.getUserId(), data.getBarcode(), ApplicantStatus.APPROVED.toString());
                    changeBookItemStatus(con, data.getBarcode(), BookStatus.ISSUED.toString());
                } else {
                    throw new SQLException("Failed inserting new book, no rows affected");
                }
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed, while inserting book", e);
            }
        }
    }

    private void changeBookItemStatus(Connection con, String barcode, String bookStatus) throws SQLException {
        String sql = "UPDATE BookItems SET book_status = ? WHERE barcode = ? ;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bookStatus);
            ps.setString(2, barcode);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0)
                throw new SQLException("Failed to update Book Item status");
        }
    }

    private void changeApplicantStatus(Connection con, int user_id, String barcode, String applicantStatus) throws SQLException {
        String sql = "UPDATE BorrowBookApplicants SET status = ? WHERE user_id = ? AND barcode = ? ;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, applicantStatus);
            ps.setInt(2, user_id);
            ps.setString(3, barcode);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0)
                throw new SQLException("Failed to update Applicant status");

        }
    }

    public void changeApplicantStatus(int userId, String barcode, String applicantStatus) throws SQLException {
        String sql = "UPDATE BorrowBookApplicants SET status = ? WHERE user_id = ? AND barcode = ? ;";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, applicantStatus);
                ps.setInt(2, userId);
                ps.setString(3, barcode);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0)
                    throw new SQLException("Failed to update Applicant status");
                else
                    con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("Transaction failed, to change applicant status", e);

            }
        }
    }


    private BorrowBookDto setBookDetails(ResultSet res) throws SQLException {
        BorrowBookDto book = new BorrowBookDto();

        book.setBorrowId(res.getInt("borrow_id"));
        book.setUserId(res.getInt("user_id"));
        book.setBarcode(res.getString("barcode"));
        book.setIssueDate(res.getTimestamp("issue_date") != null
                ? res.getTimestamp("issue_date").toLocalDateTime()
                : null);

        book.setDueDate(res.getTimestamp("due_date") != null
                ? res.getTimestamp("due_date").toLocalDateTime()
                : null);

        book.setReturnDate(res.getTimestamp("return_date") != null
                ? res.getTimestamp("return_date").toLocalDateTime()
                : null);

        return book;
    }

    public ArrayList<BorrowBookDto> getAllIssuedBooks(UserDto userData) throws SQLException {
        String sql = """
                SELECT
                    borrow_id, 
                    user_id, 
                    barcode, 
                    issue_date, 
                    due_date, 
                    return_date 
                FROM BorrowedBooks
                """;

        if (userData.getUserType().equalsIgnoreCase("member"))
            sql = sql + "WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (userData.getUserType().equalsIgnoreCase("member"))
                ps.setInt(1, userData.getUserId());

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    responseList.add(setBookDetails(res));
                }
            }
            return responseList;
        }
    }

    public ArrayList<BorrowBookDto> getIssuedBooks(UserDto userData, boolean is_returned) throws SQLException {
        String sql = """
                SELECT
                    borrow_id, 
                    user_id, 
                    barcode, 
                    issue_date, 
                    due_date, 
                    return_date 
                FROM BorrowedBooks 
                """;

        if (is_returned)
            sql = sql + "WHERE return_date IS NOT NULL ";
        else
            sql = sql + "WHERE return_date IS NULL ";

        if (userData.getUserType().equalsIgnoreCase("member"))
            sql = sql + "AND user_id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (userData.getUserType().equalsIgnoreCase("member"))
                ps.setInt(1, userData.getUserId());

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    responseList.add(setBookDetails(res));
                }
            }

            return responseList;
        }
    }


    public ArrayList<Integer> getAllBorrowedBookUsers() throws SQLException {
        String sql = "SELECT user_id FROM BorrowedBooks GROUP BY user_id;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ArrayList<Integer> userIds = new ArrayList<>();

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    userIds.add(res.getInt("user_id"));
                }
            }

            return userIds;
        }
    }


    public void collectBook(BorrowBookDto issuedBook) throws SQLException {
        String sql = "UPDATE BorrowedBooks SET return_date = CURRENT_TIMESTAMP WHERE borrow_id = ? ;";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, issuedBook.getBorrowId());
                int res = ps.executeUpdate();

                if (res > 0) {
                    changeBookItemStatus(con, issuedBook.getBarcode(), "AVAILABLE");
                } else {
                    throw new SQLException("Failed to update Books, no rows affected.");
                }

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw new SQLException("TRANSACTION FAILED");

            }
        }
    }


    public ArrayList<BorrowResponseDto> getBooksIssuedToUser(int userId) throws SQLException {
        String sql = """
                SELECT 
                    t1.borrow_id,
                    t2.barcode,
                    t2.isbn,
                    t3.title,
                    t2.shelf_id,
                    t2.book_status,
                    t2.book_condition,
                    t1.issue_date,
                    t1.due_date,
                    t1.return_date,
                    IF(
                        TIMESTAMPDIFF(DAY, t1.due_date, CURRENT_TIMESTAMP) > 0,
                        TIMESTAMPDIFF(DAY, t1.due_date, CURRENT_TIMESTAMP),
                        0
                    ) AS due_days
                FROM BorrowedBooks t1
                INNER JOIN BookItems t2 ON t1.barcode = t2.barcode
                INNER JOIN Books t3 ON t2.isbn = t3.isbn
                WHERE t1.user_id = ?
                ORDER BY t1.issue_date DESC;
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);


            ArrayList<BorrowResponseDto> list = new ArrayList<>();
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {

                    BorrowResponseDto dto = new BorrowResponseDto();

                    dto.setBorrowId(res.getInt("borrow_id"));
                    dto.setUserid(userId);
                    dto.setBarcode(res.getString("barcode"));
                    dto.setIsbn(res.getString("isbn"));
                    dto.setTitle(res.getString("title"));
                    dto.setShelfId(res.getString("shelf_id"));
                    dto.setBookStatus(res.getString("book_status"));
                    dto.setBookCondition(res.getString("book_condition"));

                    dto.setIssueDate(res.getTimestamp("issue_date") != null
                            ? res.getTimestamp("issue_date").toLocalDateTime()
                            : null);

                    dto.setDueDate(res.getTimestamp("due_date") != null
                            ? res.getTimestamp("due_date").toLocalDateTime()
                            : null);

                    dto.setReturnDate(res.getTimestamp("return_date") != null
                            ? res.getTimestamp("return_date").toLocalDateTime()
                            : null);

                    dto.setDueDays(res.getInt("due_days"));

                    list.add(dto);
                }
            }
            return list;
        }
    }
}
