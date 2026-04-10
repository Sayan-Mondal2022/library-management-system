package com.library.service;

import com.library.dto.LoginRequest;
import com.library.dao.UserDao;
import com.library.models.User;
import com.library.util.PasswordUtil;

public class LoginService {
    UserDao user = new UserDao();

    public User loginUser(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user_data;

        try {
            user_data = user.getUser(email);

            if (user_data == null) {
                throw new RuntimeException("User not found");
            }

            boolean val = PasswordUtil.verifyPassword(password, user_data.getPasswordHash());

            // This is used for Debugging only.
            // System.out.println("Verification result: " + val);

            if (!val) {
                throw new RuntimeException("Invalid user credentials, Please Try Again");
            }

            System.out.println("\nWelcome " + user_data.getUserName() + "!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user_data;
    }
}
