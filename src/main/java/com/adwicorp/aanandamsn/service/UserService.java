package com.adwicorp.aanandamsn.service;

import com.adwicorp.aanandamsn.entity.User;
import com.adwicorp.aanandamsn.exception.BusinessException;
import com.adwicorp.aanandamsn.exception.ErrorCodes;
import com.adwicorp.aanandamsn.mapper.UserMapper;
import com.adwicorp.aanandamsn.repository.UserRepository;
import com.adwicorp.aanandamsn.request.UserRequest;
import com.adwicorp.aanandamsn.request.UserUpdateRequest;
import com.adwicorp.aanandamsn.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        List<User> userList = userRepository.findByIsDeletedFalse();
        List<UserResponse> userResponses = userList.stream()
                .map(x -> userMapper.mapToUserResponse(x))
                .collect(Collectors.toList());
        return userResponses;
    }

    public String saveUser(UserRequest userRequest) {
        User user = userMapper.mapToUser(userRequest);
        try {
            userRepository.save(user);
            return "User saved successfully";
        } catch (DataIntegrityViolationException e) {
            String column = "";
            Throwable cause = e.getCause().getCause();
            if (cause != null && cause.getMessage() != null) {
                String message = cause.getMessage();
                if (message.contains("entry")) {
                    int startIndex = message.indexOf("entry") + 7;
                    int endIndex = message.indexOf(" ", startIndex);
                    column = message.substring(startIndex, endIndex).trim();
                }
            }
            throw new BusinessException(ErrorCodes.DB_EXISTING_DATA,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.DB_EXISTING_DATA) + column);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }


    } // catch + wrap + throw global exception handlers

    public String updateUser(Long userId, UserUpdateRequest userUpdateRequest) throws DataIntegrityViolationException{
        User user = userRepository.findByUserId(userId);
        user = userMapper.mapToUserUpdate(user, userUpdateRequest);
        userRepository.save(user);
        return "User updated successfully";
    }

    public String deleteUser(Long userId) {
        User user = userRepository.findByUserId(userId);
        user.setDeleted(true);
        userRepository.save(user);
        return "User deleted successfully";
    }
}
//global exception handler covering business exc & exce
//
//controller should not have any exception handeling
//
//logger implement (follow specified format, trace id, rolling file appender 100 mb each file max 10 files) log stack trace
//
//mockito testing





