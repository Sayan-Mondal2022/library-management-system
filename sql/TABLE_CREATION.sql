CREATE DATABASE LIBRARY;
USE Library;

-- Users table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,

    username VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_no VARCHAR(15) UNIQUE NOT NULL,

    address VARCHAR(255),

    password_hash VARCHAR(255) NOT NULL,

    user_type ENUM('MEMBER','LIBRARIAN','ADMIN') DEFAULT 'MEMBER',

    is_blacklisted BOOLEAN DEFAULT FALSE,
    blacklist_reason TEXT,
    blacklisted_at TIMESTAMP NULL,

    membership_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- Will store data about different authors
CREATE TABLE Authors (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    nationality VARCHAR(150)
);

-- Will store all different Genres
CREATE TABLE Genres (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Table to store the Book Metadata
CREATE TABLE Books (
	isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    
    author_id INT NOT NULL,
    genre_id INT NOT NULL,
    
    publisher VARCHAR(255),
    publication_year INT,
    edition VARCHAR(50),
    pages INT,
    language VARCHAR(50),
    description TEXT,
    
    is_deleted BOOLEAN DEFAULT FALSE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (author_id) REFERENCES Authors(id) ON DELETE RESTRICT,
	FOREIGN KEY (genre_id) REFERENCES Genres(id) ON DELETE RESTRICT
);

CREATE TABLE Section (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);



CREATE TABLE Shelves (
	id VARCHAR(100) NOT NULL,
    section_id INT NOT NULL,
    
    PRIMARY KEY (id, section_id),
    FOREIGN KEY (section_id) REFERENCES Section(id)
);


-- This will store Copy details
CREATE TABLE BookItems (
    barcode VARCHAR(50) PRIMARY KEY,

    isbn VARCHAR(20) NOT NULL,
    shelf_id VARCHAR(50) NOT NULL,

    book_status ENUM(
        'AVAILABLE',
        'LOANED',
        'RESERVED',
        'LOST',
        'DAMAGED'
    ) DEFAULT 'AVAILABLE',

    book_condition ENUM('NEW','GOOD','WORN','DAMAGED') DEFAULT 'GOOD',

    is_removed BOOLEAN DEFAULT FALSE,
    removed_at TIMESTAMP NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (isbn) REFERENCES Books(isbn),
    FOREIGN KEY (shelf_id) REFERENCES Shelves(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- This will store data related to borrowing of books
CREATE TABLE BorrowedBooks (
    borrow_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,
    barcode VARCHAR(50) NOT NULL,

    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NULL,
    return_date TIMESTAMP NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (barcode) REFERENCES BookItems(barcode),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- This table will store all the fines that will be calculated
CREATE TABLE Fines (
    fine_id INT AUTO_INCREMENT PRIMARY KEY,

    borrow_id INT NOT NULL,

    fine_amount DECIMAL(10,2) NOT NULL,
    paid_amount DECIMAL(10,2) DEFAULT 0,

    is_paid BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP NULL,

    FOREIGN KEY (borrow_id) REFERENCES BorrowedBooks(borrow_id)
);


CREATE TABLE BorrowBookApplicants(
	user_id INT,
    barcode VARCHAR(50),
    status ENUM('APPROVED', 'PENDING', 'REJECTED') DEFAULT 'PENDING',
    
    PRIMARY KEY (user_id, barcode),
    FOREIGN KEY (barcode) REFERENCES BookItems(barcode),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

SELECT * FROM Users;
SELECT * FROM Books;
SELECT * FROM BookItems;
SELECT * FROM BorrowedBooks;
SELECT * FROM Fines;