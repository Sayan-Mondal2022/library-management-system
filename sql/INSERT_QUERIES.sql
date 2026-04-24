USE library;

-- =================================================

INSERT INTO Authors (id, name) VALUES
(1, 'J.K. Rowling'),
(2, 'George Orwell'),
(3, 'Dan Brown'),
(4, 'Agatha Christie'),
(5, 'Stephen King');

-- =================================================

INSERT INTO Genres (id, name) VALUES
(1, 'Fantasy'),
(2, 'Dystopian'),
(3, 'Thriller'),
(4, 'Mystery'),
(5, 'Horror');

-- =================================================

INSERT INTO Section (id, name) VALUES
(1, 'Fiction'),
(2, 'Non-Fiction'),
(3, 'Reference'),
(4, 'Kids'),
(5, 'General');

-- =================================================

INSERT INTO Shelves (id, section_id) VALUES
('S1', 1),
('S2', 1),
('S3', 2),
('S4', 3),
('S5', 4),
('S6', 5);

-- =================================================

INSERT INTO Books (isbn, title, author_id, genre_id) VALUES
('9780747532743', 'Harry Potter and the Philosopher''s Stone', 1, 1),
('9780451524935', '1984', 2, 2),
('9780307474278', 'The Da Vinci Code', 3, 3),
('9780062073488', 'Murder on the Orient Express', 4, 4),
('9781501142970', 'The Shining', 5, 5);

-- =================================================

INSERT INTO BookItems (barcode, isbn, shelf_id, book_status, book_condition, is_removed) VALUES
('B001', '9780747532743', 'S1', 'AVAILABLE', 'NEW', FALSE),
('B002', '9780747532743', 'S1', 'ISSUED', 'GOOD', FALSE),
('B003', '9780451524935', 'S2', 'AVAILABLE', 'GOOD', FALSE),
('B004', '9780451524935', 'S2', 'RESERVED', 'GOOD', FALSE),
('B005', '9780307474278', 'S3', 'AVAILABLE', 'NEW', FALSE),
('B006', '9780307474278', 'S3', 'ISSUED', 'DAMAGED', FALSE),
('B007', '9780062073488', 'S4', 'AVAILABLE', 'GOOD', FALSE),
('B008', '9780062073488', 'S4', 'LOST', 'DAMAGED', FALSE),
('B009', '9781501142970', 'S5', 'AVAILABLE', 'NEW', FALSE),
('B010', '9781501142970', 'S6', 'ISSUED', 'GOOD', FALSE);

-- =================================================

INSERT INTO BorrowBookApplicants (user_id, barcode, status)
VALUES
(2, 'B001', 'PENDING'),
(3, 'B003', 'PENDING'),
(3, 'B005', 'PENDING'),
(4, 'B007', 'PENDING'),
(4, 'B009', 'PENDING');

-- =================================================
