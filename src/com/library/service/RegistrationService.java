package com.library.service;

import com.library.models.User;
import com.library.dao.UserDao;
import com.library.dto.RegistrationRequest;
import com.library.util.PasswordUtil;

import java.sql.SQLException;


public class RegistrationService {
    private final UserDao userDao = new UserDao();

    public User registerUser(RegistrationRequest request) throws RuntimeException{
        try {
            User user = new User();

            user.setUserName(request.getUserName());
            user.setAddress(request.getAddress());
            user.setEmail(request.getEmail());
            user.setPhoneNo(request.getPhoneNo());
            user.setUserType(request.getUserType());
            user.setPasswordHash(PasswordUtil.hashPassword(request.getPassword()));

            userDao.save(user);
            return user;

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
