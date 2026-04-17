package com.library.service;

import com.library.dao.BorrowBookDao;
import com.library.dto.BorrowBookApplicantsDto;
import com.library.dto.BorrowBookDto;
import com.library.models.BorrowBook;

import java.util.ArrayList;

public class BorrowBookService {
    private BorrowBookDao dao = new BorrowBookDao();

    public ArrayList<BorrowBookApplicantsDto> getPendingApplicants() throws RuntimeException{
        return dao.getPendingApplicants();
    }

    public void issueBook(BorrowBookDto data){
        BorrowBook bookData = new BorrowBook();

        bookData.setUserId(data.getUserId());
        bookData.setBarcode(data.getBarcode());
        bookData.setIssueDate(data.getIssueDate());
        bookData.setDueDate(data.getDueDate());

        dao.issueBook(bookData);
    }


    public ArrayList<BorrowBookDto> getIssuedBooks() throws RuntimeException{
        return dao.getIssuedBooks();
    }
}
