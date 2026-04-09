package com.library.service;

import com.library.models.User;
import com.library.dao.UserDao;
import com.library.dto.RegistrationRequest;
import com.library.util.PasswordUtil;


public class RegistrationService {
    private UserDao userDao = new UserDao();

    public void registerUser(RegistrationRequest request) {
        // Get the data.
        String user_name = request.getUserName();
        String address = request.getAddress();
        String email = request.getEmail();
        String phone_no = request.getPhoneNo();
        String user_type = request.getUserType();
        String hashedPassword = PasswordUtil.hashPassword(request.getPassword());


        // Create the user using the Constructor.
        User user = new User(
                user_name,
                address,
                phone_no,
                email,
                hashedPassword,
                user_type
        );

        userDao.save(user);
    }
}
