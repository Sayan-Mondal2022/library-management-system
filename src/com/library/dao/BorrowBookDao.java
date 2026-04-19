package com.library.dao;

import com.library.db.DBConnection;
import com.library.dto.ApplicantsDto;
import com.library.dto.BorrowBookDto;
import com.library.dto.BorrowResponseDto;
import com.library.dto.FinedDetailsDto;
import com.library.enums.ApplicantStatus;
import com.library.enums.BookStatus;
import com.library.models.BorrowBook;

import java.sql.*;
import java.util.ArrayList;

public class BorrowBookDao {
    public ArrayList<ApplicantsDto> getPendingApplicants() throws RuntimeException {
        String sql = """
                SELECT
                    user_id,
                    barcode, 
                    status 
                FROM BorrowBookApplicants 
                WHERE status = PENDING;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            ArrayList<ApplicantsDto> applicantList = new ArrayList<>();

            while (res.next()) {
                ApplicantsDto dto = new ApplicantsDto();

                dto.setUserId(res.getInt("user_id"));
                dto.setBarcode(res.getString("barcode"));
                dto.setStatus(res.getString("status"));

                applicantList.add(dto);
            }

            return applicantList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // This function will update the BorrowedBooks table.
    public void issueBook(BorrowBook data) throws RuntimeException {
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
                    changeBookItemStatus(con, data.getBarcode(), BookStatus.LOANED.toString());
                }
                con.commit();

            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("TRANSACTION FAILED ");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changeBookItemStatus(Connection con, String barcode, String bookStatus) throws RuntimeException {
        String sql = "UPDATE BookItems SET book_status = ? WHERE barcode = ? ;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bookStatus);
            ps.setString(2, barcode);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changeApplicantStatus(Connection con, int user_id, String barcode, String applicantStatus) throws RuntimeException {
        String sql = "UPDATE BorrowBookApplicants SET status = ? WHERE user_id = ? AND barcode = ? ;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, applicantStatus);
            ps.setInt(2, user_id);
            ps.setString(3, barcode);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changeApplicantStatus(int user_id, String barcode, String applicantStatus) throws RuntimeException {
        String sql = "UPDATE BorrowBookApplicants SET status = ? WHERE user_id = ? AND barcode = ? ;";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, applicantStatus);
                ps.setInt(2, user_id);
                ps.setString(3, barcode);

                ps.executeUpdate();
                con.commit();

            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("TRANSACTION FAILED");

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private BorrowBookDto setBookDetails(ResultSet res) throws SQLException{
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

    public ArrayList<BorrowBookDto> getAllIssuedBooks() throws RuntimeException {
        String sql = """
                SELECT 
                    borrow_id, 
                    user_id, 
                    barcode, 
                    issue_date, 
                    due_date, 
                    return_date 
                FROM BorrowedBooks;
                """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();
            while (res.next()) {
                responseList.add(setBookDetails(res));
            }

            return responseList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<BorrowBookDto> getIssuedBooks(boolean is_returned) throws RuntimeException {
        String sql = """
                SELECT 
                    borrow_id, 
                    user_id, 
                    barcode, 
                    issue_date, 
                    due_date, 
                    return_date 
                FROM BorrowedBooks 
                WHERE return_date IS NULL;
                """;

        if (is_returned)
            sql = "SELECT borrow_id, user_id, barcode, issue_date, due_date, return_date FROM BorrowedBooks WHERE return_date IS NOT NULL;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();
            while (res.next()) {
                responseList.add(setBookDetails(res));
            }

            return responseList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private BorrowBookDto setOverdueDetails(ResultSet res) throws SQLException{
        BorrowBookDto dto = new BorrowBookDto();

        dto.setBorrowId(res.getInt("borrow_id"));
        dto.setUserId(res.getInt("user_id"));
        dto.setBarcode(res.getString("barcode"));
        dto.setDueDays(res.getInt("day_diff"));

        return dto;
    }

    public ArrayList<BorrowBookDto> getOverdueUser(int userId) throws RuntimeException {
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
            ResultSet res = ps.executeQuery();

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();
            while (res.next()) {
                responseList.add(setOverdueDetails(res));
            }

            return responseList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<BorrowBookDto> getAllOverdueUsers() throws RuntimeException {
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
            ResultSet res = ps.executeQuery();

            ArrayList<BorrowBookDto> responseList = new ArrayList<>();
            while (res.next()) {
                responseList.add(setOverdueDetails(res));
            }

            return responseList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fineAllUsers(ArrayList<FinedDetailsDto> finedUsers) throws RuntimeException {
        String sql = "INSERT INTO Fines (borrow_id, fine_amount) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            for (FinedDetailsDto dto : finedUsers) {
                ps.setInt(1, dto.getBorrowId());
                ps.setDouble(2, dto.getFineAmount());
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FinedDetailsDto setFinedDetails(ResultSet res) throws SQLException{
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


    public ArrayList<FinedDetailsDto> getAllFinedUsers() throws RuntimeException {
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
            ResultSet res = ps.executeQuery();

            ArrayList<FinedDetailsDto> list = new ArrayList<>();
            while (res.next()) {
                list.add(setFinedDetails(res));
            }
            return list;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<FinedDetailsDto> getFinedUser(int userId) throws RuntimeException {
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
            ResultSet res = ps.executeQuery();

            ArrayList<FinedDetailsDto> list = new ArrayList<>();
            while (res.next()) {
                list.add(setFinedDetails(res));
            }
            return list;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> getAllBorrowedBookUsers() throws RuntimeException {
        String sql = "SELECT user_id FROM BorrowedBooks GROUP BY user_id;";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet res = ps.executeQuery();

            ArrayList<Integer> userIds = new ArrayList<>();
            while (res.next()) {
                userIds.add(res.getInt("user_id"));
            }

            return userIds;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void collectBook(BorrowBookDto issuedBook) throws RuntimeException {
        String sql = "UPDATE BorrowedBooks SET return_date = CURRENT_TIMESTAMP WHERE borrow_id = ? ;";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, issuedBook.getBorrowId());
                int res = ps.executeUpdate();

                if (res > 0) {
                    changeBookItemStatus(con, issuedBook.getBarcode(), "AVAILABLE");
                }

                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("TRANSACTION FAILED");

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<BorrowResponseDto> getBooksIssuedToUser(int userId) {
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
            ResultSet res = ps.executeQuery();

            ArrayList<BorrowResponseDto> list = new ArrayList<>();

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

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
