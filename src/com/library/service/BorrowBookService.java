package com.library.service;

import com.library.dao.BookItemDao;
import com.library.dao.BorrowBookDao;
import com.library.dto.BorrowBookApplicantsDto;
import com.library.dto.BorrowBookDto;
import com.library.dto.BorrowResponseDto;
import com.library.dto.FinedDetailsDto;
import com.library.enums.ApplicantStatus;
import com.library.enums.BookStatus;
import com.library.models.BorrowBook;

import java.util.ArrayList;

public class BorrowBookService {
    private final int PER_DAY_CHARGE = 5;
    private final BorrowBookDao dao;

    public BorrowBookService(BorrowBookDao dao) {
        this.dao = dao;

    }


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


    public ArrayList<BorrowBookApplicantsDto> getPendingApplicants() throws RuntimeException {
        return dao.getPendingApplicants();
    }

    public void issueBook(BorrowBookDto data) throws RuntimeException {
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

    public void rejectApplicant(int userId, String barcode) throws RuntimeException {
        dao.changeApplicantStatus(userId, barcode, ApplicantStatus.REJECTED.toString());

    }


    public ArrayList<BorrowBookDto> getAllIssuedBooks() throws RuntimeException {
        return dao.getAllIssuedBooks();
    }

    public ArrayList<BorrowBookDto> getIssuedBooks(boolean is_returned) throws RuntimeException {
        return dao.getIssuedBooks(is_returned);
    }


    public ArrayList<BorrowBookDto> getAllOverdueUsers() throws RuntimeException {
        return dao.getAllOverdueUsers();
    }

    public ArrayList<FinedDetailsDto> getAllFinedUsers() throws RuntimeException {
        return dao.getAllFinedUsers();
    }


    public void fineAllUsers() throws RuntimeException {
        try {
            ArrayList<BorrowBookDto> users = dao.getAllOverdueUsers();
            ArrayList<FinedDetailsDto> finedUsers = calculateFines(users);
            dao.fineAllUsers(finedUsers);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void fineUser(int userId) {
        try {
            ArrayList<BorrowBookDto> users = dao.getOverdueUser(userId);
            ArrayList<FinedDetailsDto> finedUsers = calculateFines(users);

            dao.fineAllUsers(finedUsers);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<BorrowBookDto> getOverdueUser(int userId) throws RuntimeException {
        return dao.getOverdueUser(userId);
    }

    public ArrayList<FinedDetailsDto> getFinedUser(int userId) throws RuntimeException {
        return dao.getFinedUser(userId);
    }


    public ArrayList<Integer> getAllUsers() throws RuntimeException {
        return dao.getAllBorrowedBookUsers();
    }


    public void collectBook(BorrowBookDto issuedBook) throws RuntimeException {
        dao.collectBook(issuedBook);
    }


    public ArrayList<BorrowResponseDto> getBooksIssuedToUser(int userId) {
        return dao.getBooksIssuedToUser(userId);
    }
}
