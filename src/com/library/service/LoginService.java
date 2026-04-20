package com.library.service;

import com.library.dto.LoginRequest;
import com.library.dao.UserDao;
import com.library.dto.UserDto;
import com.library.util.PasswordUtil;

public class LoginService {
    private final UserDao userDao = new UserDao();

    public UserDto loginUser(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        UserDto userData;

        try {
            userData = userDao.getUser("email", email);

            if (userData == null) {
                throw new RuntimeException("User not found");
            }

            boolean val = PasswordUtil.verifyPassword(password, userData.getPasswordHash());

            if (!val) {
                throw new RuntimeException("Invalid user credentials, Please Try Again");
            }
            return userData;

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
