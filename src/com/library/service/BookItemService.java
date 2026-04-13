package com.library.service;

import com.library.dao.BookItemDao;
import com.library.dao.BookSectionDao;
import com.library.dao.BookShelvesDao;
import com.library.dto.BookItemDto;
import com.library.enums.BookCondition;
import com.library.enums.BookStatus;
import com.library.models.BookItem;

import java.util.List;


public class BookItemService {
    private BookItemDao dao = new BookItemDao();
    private BookSectionDao sectionDao = new BookSectionDao();
    private BookShelvesDao shelvesDao = new BookShelvesDao();

    public void addBookItem(BookItemDto dto) throws RuntimeException {
        try {
            if (dto.getIsbn() == null || dto.getIsbn().isEmpty() || dto.getBarcode() == null || dto.getBarcode().isEmpty())
                throw new RuntimeException("Without ISBN or BARCODE a book copy is INVALID");
            if (dto.getSection_name() == null || dto.getSection_name().isEmpty())
                throw new RuntimeException("A book can't be added with a valid Section Name");
            if (dto.getShelf_id() == null || dto.getShelf_id().isEmpty())
                throw new RuntimeException("A Book can't be added without a valid Shelf id");

            BookItem book = new BookItem();

            book.setIsbn(dto.getIsbn());
            book.setBarcode(dto.getBarcode());
            book.setBook_condition(dto.getBook_condition());
            book.setBook_status(dto.getBook_status());

            book.setShelf_id(dto.getShelf_id());
            try {
                int section_id = sectionDao.getOrCreateSection(dto.getSection_name());
                shelvesDao.addShelfId_SectionId(dto.getShelf_id(), section_id);

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }

            dao.addBookItem(book);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookItemDto> getAllBookCopies(boolean is_removed) {
        return dao.getAllBookCopies(is_removed);
    }

    public BookItemDto getBookItem(String barcode) {
        return dao.getBookItemByBarcode(barcode);
    }

    public void updateBookItem(BookItemDto updated, BookItemDto existing) throws RuntimeException {
        try {
            if (existing == null)
                throw new RuntimeException("Existing book item not found");

            // These two values remains unchanged.
            String isbn = existing.getIsbn();
            String barcode = existing.getBarcode();

            String sectionName = (updated.getSection_name() != null && !updated.getSection_name().isEmpty())
                    ? updated.getSection_name()
                    : existing.getSection_name();

            String shelfId = (updated.getShelf_id() != null && !updated.getShelf_id().isEmpty())
                    ? updated.getShelf_id()
                    : existing.getShelf_id();

            BookCondition condition = (updated.getBook_condition() != null)
                    ? updated.getBook_condition()
                    : BookCondition.valueOf(existing.getCondition());

            BookStatus status = (updated.getBook_status() != null)
                    ? updated.getBook_status()
                    : BookStatus.valueOf(existing.getStatus());

            // Validations
            if (sectionName == null || sectionName.isEmpty())
                throw new RuntimeException("Section name cannot be empty");

            if (shelfId == null || shelfId.isEmpty())
                throw new RuntimeException("Shelf ID cannot be empty");

            // Create updated BookItem model
            BookItem book = new BookItem();

            book.setIsbn(isbn);
            book.setBarcode(barcode);
            book.setBook_condition(condition);
            book.setBook_status(status);
            book.setShelf_id(shelfId);

            int section_id = sectionDao.getOrCreateSection(sectionName);
            shelvesDao.addShelfId_SectionId(shelfId, section_id);

            dao.updateBookItem(book);

        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update BookItem: " + e.getMessage(), e);
        }
    }
}
