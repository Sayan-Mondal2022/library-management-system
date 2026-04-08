package com.library.service;

// Import the User and Account class
import com.library.models.Account;
import com.library.models.User;

// Import the AccountDao and UserDao class for creating the Account and User in the DB
import com.library.dao.UserDao;
import com.library.dao.AccountDao;

// Import the RegistrationRequest class from DTO
import com.library.dto.RegistrationRequest;


public class RegistrationService {
    private UserDao userDao = new UserDao();
    private AccountDao accountDao = new AccountDao();

    public void registerUser(RegistrationRequest request) {

        // First Create User
        User user = new User();

        user.setUserName(request.getUserName());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhoneNo(request.getPhoneNo());

        // returns generated ID
        int userId = userDao.save(user);


        // Second step Create Account
        Account account = new Account();

        account.setUserId(userId);
        account.setAccountType(request.getAccountType());

        String hashedPassword = hashPassword(request.getPassword());
        account.setPasswordHash(hashedPassword);

        accountDao.save(account);
    }

    private String hashPassword(String password) {
        // Return hased password (Add logic later)
        String hashedPassword = password;

        return hashedPassword;
    }
}
