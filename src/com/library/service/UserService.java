package com.library.service;

import com.library.dao.UserDao;
import com.library.dto.UserSummaryDto;
import com.library.dto.UserDto;
import com.library.models.User;
import com.library.util.PasswordUtil;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDao dao = new UserDao();


    public List<UserDto> getAllUsers(String userType) throws SQLException {
        return dao.getAllUsers(userType);
    }

    public UserDto getUser(int userId) throws SQLException {
        return dao.getUser("id", String.valueOf(userId));
    }

    public void updateUserDetails(UserDto existingDetails, UserDto updatedDetails) throws SQLException{
        User finalUser = mergeUserDetails(existingDetails, updatedDetails);
        dao.updateUserDetails(finalUser);
    }

    private User mergeUserDetails(UserDto existingDetails, UserDto updatedDetails) {
        User finalUser = new User();

        finalUser.setUserId(existingDetails.getUserId());

        finalUser.setUserName(
                updatedDetails.getUserName() != null
                        ? updatedDetails.getUserName()
                        : existingDetails.getUserName()
        );

        finalUser.setEmail(
                updatedDetails.getEmail() != null
                        ? updatedDetails.getEmail()
                        : existingDetails.getEmail()
        );

        finalUser.setPasswordHash(
                updatedDetails.getPassword() != null
                        ? PasswordUtil.hashPassword(updatedDetails.getPassword())
                        : existingDetails.getPasswordHash()
        );

        finalUser.setAddress(
                updatedDetails.getAddress() != null
                        ? updatedDetails.getAddress()
                        : existingDetails.getAddress()
        );

        finalUser.setPhoneNo(
                updatedDetails.getPhoneNo() != null
                        ? updatedDetails.getPhoneNo()
                        : existingDetails.getPhoneNo()
        );

        return finalUser;
    }


    public UserSummaryDto getUserSummary(int userId) throws SQLException {
        return dao.getUserSummary(userId);
    }


    public void blacklistUser(int userId) throws SQLException{
        dao.blacklistUser(userId);
    }
}
