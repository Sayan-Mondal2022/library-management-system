package com.library.service;

import com.library.dao.*;
import com.library.dto.BookDto;
import com.library.dto.BookItemDto;
import com.library.enums.BookCondition;
import com.library.enums.BookStatus;
import com.library.models.BookItem;

import java.sql.SQLException;
import java.util.List;


public class BookItemService {
    private final BookItemDao dao = new BookItemDao();
    private final SectionDao sectionDao = new SectionDao();
    private final ShelvesDao shelvesDao = new ShelvesDao();


    public void addBookItem(BookItemDto dto) throws RuntimeException {
        try {
            if (dto.getIsbn() == null || dto.getIsbn().isEmpty() || dto.getBarcode() == null || dto.getBarcode().isEmpty())
                throw new RuntimeException("Without ISBN or BARCODE a book copy is INVALID");
            if (dto.getSectionName() == null || dto.getSectionName().isEmpty())
                throw new RuntimeException("A book can't be added with a valid Section Name");
            if (dto.getShelfId() == null || dto.getShelfId().isEmpty())
                throw new RuntimeException("A Book can't be added without a valid Shelf id");

            BookItem book = new BookItem();

            book.setIsbn(dto.getIsbn());
            book.setBarcode(dto.getBarcode());
            book.setBookCondition(dto.getBookCondition());
            book.setBookStatus(dto.getBookStatus());

            book.setShelfId(dto.getShelfId());
            try {
                int section_id = sectionDao.getOrCreateSection(dto.getSectionName());
                shelvesDao.addShelfId(dto.getShelfId(), section_id);

            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(e);
            }

            dao.addBookItem(book);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBookItem(String barcode) {
        dao.deleteBookItem(barcode);
    }

    public void updateBookItem(BookItemDto updated, BookItemDto existing) throws RuntimeException {
        try {
            BookItem book = mergeBook(updated, existing);

            if (book.getSection() == null || book.getSection().isEmpty()) {
                throw new RuntimeException("Section name cannot be null while updating");
            }

            int section_id = sectionDao.getOrCreateSection(book.getSection());
            shelvesDao.addShelfId(book.getShelfId(), section_id);
            dao.updateBookItem(book);

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Failed to update BookItem: " + e.getMessage(), e);
        }
    }

    private BookItem mergeBook(BookItemDto updated, BookItemDto existing) {
        try {
            if (existing == null)
                throw new RuntimeException("Existing book item not found");

            BookItem finalCopy = new BookItem();
            finalCopy.setIsbn(existing.getIsbn());
            finalCopy.setBarcode(existing.getBarcode());

            finalCopy.setSection((updated.getSectionName() != null && !updated.getSectionName().isEmpty())
                    ? updated.getSectionName()
                    : existing.getSectionName());

            finalCopy.setShelfId((updated.getShelfId() != null && !updated.getShelfId().isEmpty())
                    ? updated.getShelfId()
                    : existing.getShelfId());

            finalCopy.setBookCondition((updated.getBookCondition() != null)
                    ? updated.getBookCondition()
                    : BookCondition.valueOf(existing.getCondition()));

            finalCopy.setBookStatus((updated.getBookStatus() != null)
                    ? updated.getBookStatus()
                    : BookStatus.valueOf(existing.getStatus()));

            return finalCopy;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDto> getIsbnTitle() throws SQLException{
        return new BookDao().getBookIsbnTitle(false);
    }


    public List<BookItemDto> getAllCopies(boolean is_removed) {
        return dao.getAllCopies(is_removed);
    }

    public BookItemDto getBookItem(String barcode) {
        return dao.getBookItemByBarcode(barcode);
    }



    public List<BookItemDto> getCopiesByStatus(String bookStatus) throws SQLException {
        return dao.getCopiesByStatus(bookStatus);
    }


    public List<BookItemDto> getCopiesByCondition(String bookCondition) throws SQLException {
        return dao.getCopiesByCondition(bookCondition);
    }



    public List<BookItemDto> getCopiesByAuthorName(String authorName, String bookStatus) throws SQLException {
        return dao.getCopiesByAuthorName(authorName, bookStatus);
    }

    public List<BookItemDto> getCopiesByGenreName(String genreName, String bookStatus) throws SQLException {
        return dao.getCopiesByGenreName(genreName, bookStatus);
    }

    public List<BookItemDto> getCopiesBySectionName(String sectionName, String bookStatus) throws SQLException {
        return dao.getCopiesBySectionName(sectionName, bookStatus);
    }



    public List<String> getAllAuthors() throws SQLException{
        return new AuthorDao().getAllAuthorNames();
    }

    public List<String> getAllGenres() throws SQLException{
        return new GenreDao().getAllGenreNames();
    }

    public List<String> getAllSections() throws SQLException{
        return sectionDao.getAllSectionNames();
    }

    public void borrowBook(int userId, String barcode) throws SQLException{
        dao.borrowBook(userId, barcode);
    }
}
