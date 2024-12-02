package com.adwicorp.aanandamsn.service;

import com.adwicorp.aanandamsn.model.entity.UserEntity;
import com.adwicorp.aanandamsn.exception.BusinessException;
import com.adwicorp.aanandamsn.exception.ErrorCodes;
import com.adwicorp.aanandamsn.mapper.UserMapper;
import com.adwicorp.aanandamsn.model.request.UpdateUserRequest;
import com.adwicorp.aanandamsn.repository.UserRepository;
import com.adwicorp.aanandamsn.model.request.UserRequest;
import com.adwicorp.aanandamsn.model.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<UserResponse> getAllUsers() {
        try {
            List<UserEntity> userList = userRepository.findByDeletedFalse();
            List<UserResponse> userResponses = userList.stream()
                    .map(x -> userMapper.mapToUserResponse(x))
                    .collect(Collectors.toList());
            return userResponses;
        } catch (Exception e) {
            logger.error("Error occurred while fetching user details : {}", e.getStackTrace());
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }
    }

    public String saveUser(UserRequest userRequest) {
        UserEntity user = userMapper.mapToUser(userRequest);
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
                logger.error("Error occurred while saving user error {}", e.getStackTrace());
                throw new BusinessException(ErrorCodes.DB_EXISTING_DATA,
                        ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.DB_EXISTING_DATA) + column);
            }
            logger.error("Error occurred while saving user error {}", e.getStackTrace());
            throw new BusinessException(ErrorCodes.PARAM_MISSING,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.PARAM_MISSING));
        } catch (Exception e) {
            logger.error("Error occurred while saving user error {}", e.getStackTrace());
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }


    }

    public String updateUser(Long userId, UpdateUserRequest updateUserRequest) throws DataIntegrityViolationException{
        try {
            UserEntity user = userRepository.findByUserId(userId);
            user = userMapper.mapToUserUpdate(user, updateUserRequest);
            userRepository.save(user);
            return "User updated successfully";
        } catch (Exception e) {
            logger.error("Error occurred while updating user details : {}", e.getStackTrace());
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }
    }
}





