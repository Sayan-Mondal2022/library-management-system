package com.library.service;

import com.library.dao.BookItemDao;
import com.library.dao.BorrowBookDao;
import com.library.dao.FineDao;
import com.library.dto.ApplicantsDto;
import com.library.dto.BorrowBookDto;
import com.library.dto.BorrowResponseDto;
import com.library.dto.FinedDetailsDto;
import com.library.enums.ApplicantStatus;
import com.library.models.BorrowBook;

import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowBookService {
    private final BorrowBookDao dao = new BorrowBookDao();
    private final FineDao fineDao = new FineDao();
    private final int PER_DAY_CHARGE = 5;


    private ArrayList<FinedDetailsDto> calculateFines(ArrayList<BorrowBookDto> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<FinedDetailsDto> finedUsers = new ArrayList<>();

        for (BorrowBookDto dto : users) {
            FinedDetailsDto finedUserDto = new FinedDetailsDto();

            finedUserDto.setBorrowId(dto.getBorrowId());
            finedUserDto.setFineAmount(dto.getDueDays() * PER_DAY_CHARGE);

            finedUsers.add(finedUserDto);
        }

        return finedUsers;
    }


    public ArrayList<ApplicantsDto> getPendingApplicants() throws SQLException {
        return dao.getPendingApplicants();
    }

    public void issueBook(BorrowBookDto data) throws SQLException {
        try {
            BookItemDao bookItemDao = new BookItemDao();
            int res = bookItemDao.getBookStatus(data.getBarcode());

            if (res != 1) {
                throw new RuntimeException("BOOK IS NOT AVAILABLE!");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        BorrowBook bookData = new BorrowBook();
        bookData.setUserId(data.getUserId());

        if (data.getBarcode() == null || data.getBarcode().isEmpty())
            throw new RuntimeException("INVALID BARCODE");
        bookData.setBarcode(data.getBarcode());

        if (data.getDueDate().isBefore(data.getIssueDate()))
            throw new RuntimeException("INVALID DUE DATE");

        bookData.setIssueDate(data.getIssueDate());
        bookData.setDueDate(data.getDueDate());

        dao.issueBook(bookData);
    }

    public void rejectApplicant(int userId, String barcode) throws SQLException {
        dao.changeApplicantStatus(userId, barcode, ApplicantStatus.REJECTED.toString());

    }


    public ArrayList<BorrowBookDto> getAllIssuedBooks() throws SQLException {
        return dao.getAllIssuedBooks();
    }

    public ArrayList<BorrowBookDto> getIssuedBooks(boolean is_returned) throws SQLException {
        return dao.getIssuedBooks(is_returned);
    }


    public ArrayList<Integer> getAllUsers() throws SQLException {
        return dao.getAllBorrowedBookUsers();
    }



    public ArrayList<BorrowResponseDto> getBooksIssuedToUser(int userId) throws SQLException {
        return dao.getBooksIssuedToUser(userId);
    }



    public ArrayList<BorrowBookDto> getAllOverdueUsers() throws SQLException {
        return fineDao.getAllOverdueUsers();
    }

    public ArrayList<FinedDetailsDto> getAllFinedUsers() throws SQLException {
        return fineDao.getAllFinedUsers();
    }


    public void fineAllUsers() throws SQLException {
        try {
            ArrayList<BorrowBookDto> users = fineDao.getAllOverdueUsers();
            ArrayList<FinedDetailsDto> finedUsers = calculateFines(users);
            fineDao.fineAllUsers(finedUsers);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void fineUser(int userId) throws SQLException{
        ArrayList<BorrowBookDto> users = fineDao.getOverdueUser(userId);
        ArrayList<FinedDetailsDto> finedUsers = calculateFines(users);

        fineDao.fineAllUsers(finedUsers);

    }


    public ArrayList<BorrowBookDto> getOverdueUser(int userId) throws SQLException {
        return fineDao.getOverdueUser(userId);
    }

    public ArrayList<FinedDetailsDto> getFinedUser(int userId) throws SQLException {
        return fineDao.getFinedUser(userId);
    }


    public void collectBook(BorrowBookDto issuedBook) throws SQLException {
        dao.collectBook(issuedBook);
    }

    public void collectFine(int borrowId) throws SQLException{
        fineDao.collectFine(borrowId);
    }

    public void collectFine(int borrowId, int dueDays) throws SQLException{
        double fineAmount = dueDays * PER_DAY_CHARGE;
        fineDao.collectFine(borrowId, fineAmount);
    }

    public int getDueDays(int borrowId) throws SQLException{
        return fineDao.getDueDays(borrowId);
    }

    public boolean checkFine(int borrowId) throws SQLException {
        return fineDao.checkFine(borrowId);
    }
}
