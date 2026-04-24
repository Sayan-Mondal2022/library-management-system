# Library Management System

Welcome to the **Library Management System**! This application is designed to automate and streamline the daily operations of a library. It aims to solve the difficulties of manually managing book inventory, tracking issued records, and maintaining member data by providing a robust and efficient computerized solution.

## Project Overview
The Library Management System is a comprehensive, console-based application built to facilitate seamless interaction between librarians and library members. It allows administrators (librarians) to manage the entire book inventory including authors, genres, and physical book copies while enabling members to browse, borrow, and return books. The system is backed by a structured relational database that ensures data integrity and supports features like fine calculations and role-based access control.

## Tech Stack
The project leverages the following technologies:
- **Java**: Core programming language used for the application logic and object-oriented structure.
- **MySQL**: Relational Database Management System used for storing users, books, and borrowing records.
- **JDBC (Java Database Connectivity)**: Used to connect the Java application with the MySQL database.
- **Dotenv**: Used to manage environment variables for secure database connections.
- **jBCrypt**: Employed for secure password hashing.

## Key Features of the Project
- **User Authentication & Authorization**: Secure login and registration system with distinct roles: Member, Librarian, and Admin. Passwords are securely hashed.
- **Book & Inventory Management**: Comprehensive management of Books, Authors, Genres, Sections, and physical Shelves.
- **Borrowing & Returns System**: Tracking book issuances, due dates, and return dates, including an approval workflow for book borrowing applications.
- **Fine Management**: Automated calculation and tracking of fines for overdue or damaged books.
- **Role-Based Workflows**: Tailored menus and capabilities depending on whether the user is a Librarian or a regular Member.
- **Blacklist Management**: System ability to flag and restrict access for non-compliant users.

## Installation and Setup Instructions

Follow these step-by-step instructions to get the project running locally:

**1. Prerequisites:**
- Ensure you have **Java Development Kit (JDK) 8 or higher** installed.
- Ensure you have **MySQL Server** installed and running.

**2. Clone the Repository:**
```bash
git clone https://github.com/Sayan-Mondal2022/library-management-system.git
cd library-management-system
```

**3. Database Setup:**
- Open your MySQL client (e.g., MySQL Workbench or Command Line).
- Run the SQL script located in `sql/TABLE_CREATION.sql` to create the `Library` database and its tables.
- (Optional) Run `sql/INSERT_QUERIES.sql` if you want to populate the database with initial dummy data.

**4. Environment Configuration:**
- In the root directory, locate or create a `.env` file.
- Add your database credentials as follows:
  ```env
  DB_URL=jdbc:mysql://127.0.0.1:3306/Library
  USER=root
  DB_PASS=your_mysql_password
  ```

**5. Add External Libraries:**
- Ensure the following JAR files (indicated in the `.iml` configuration) are added to your Java Classpath:
  - `mysql-connector-j-9.6.0`
  - `java-dotenv-5.2.2`
  - `jbcrypt-0.4`
  - `kotlin-stdlib-2.3.20`

**6. Compile and Run:**
- Compile the Java source files in the `src` directory.
- Run the `com.library.Main` class to start the application.

## Future Enhancements
- **Graphical User Interface (GUI)**: Transitioning from a console-based app to a graphical interface using JavaFX or a Web Frontend.
- **Email/SMS Notifications**: Automated alerts for due dates, overdue books, and successful account registrations.
- **Advanced Reporting**: Generating detailed analytics on borrowing trends, most popular genres, and fine collections.
- **Payment Gateway Integration**: Allowing users to pay fines online directly through the application.

## Acknowledgements
This project relies on several external JAR libraries and technologies to function seamlessly. Special thanks to the developers and maintainers of:
- **[JDBC (Java Database Connectivity)](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)**: For providing the API that enables seamless interaction between Java and relational databases.
- **[MySQL Connector/J](https://dev.mysql.com/doc/connector-j/en/)**: The official JDBC driver for MySQL, enabling our application to connect to the MySQL server.
- **[jBCrypt](https://www.mindrot.org/projects/jBCrypt/)**: A robust Java implementation of OpenBSD's Blowfish password hashing code, keeping our user passwords secure.
- **[dotenv-java](https://github.com/cdimascio/dotenv-java)**: For porting the popular Ruby dotenv library to Java, allowing us to manage environment variables securely.
- **[Kotlin Standard Library](https://kotlinlang.org/api/latest/jvm/stdlib/)**: Included as a dependency to support `dotenv-java` and provide essential functional extensions.

## Thank You Note
Thank you for taking the time to view and explore this project! Your interest in this Library Management System is greatly appreciated, and I hope it serves as a solid demonstration of effective software engineering and database management principles.

## Open for Collaboration
I am always open to collaboration, constructive feedback, and contributions! If you have any ideas for new features, find a bug, or want to improve the codebase, please feel free to fork the repository, open an issue, or submit a pull request. Let's learn and build great software together!