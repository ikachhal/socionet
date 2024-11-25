package com.adwicorp.aanandamsn.service;

import com.adwicorp.aanandamsn.controller.UserController;
import com.adwicorp.aanandamsn.entity.User;
import com.adwicorp.aanandamsn.exception.BusinessException;
import com.adwicorp.aanandamsn.exception.ErrorCodes;
import com.adwicorp.aanandamsn.mapper.UserMapper;
import com.adwicorp.aanandamsn.repository.UserRepository;
import com.adwicorp.aanandamsn.request.UserRequest;
import com.adwicorp.aanandamsn.request.UserUpdateRequest;
import com.adwicorp.aanandamsn.response.UserResponse;
import com.adwicorp.aanandamsn.traceconfig.TraceContext;
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
        String traceId = TraceContext.getTraceId();
        logger.info("UserService::GetAllUsers::TraceID {}", traceId);
        try {
            List<User> userList = userRepository.findByIsDeletedFalse();
            List<UserResponse> userResponses = userList.stream()
                    .map(x -> userMapper.mapToUserResponse(x))
                    .collect(Collectors.toList());
            return userResponses;
        } catch (Exception e) {
            logger.info("UserService::SaveUser::TraceID {}::Error {}", traceId, e.getStackTrace());
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }
    }

    public String saveUser(UserRequest userRequest) {
        String traceId = TraceContext.getTraceId();
        logger.info("UserService::SaveUser::TraceID {}", traceId);
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
            logger.info("UserService::SaveUser::TraceID {}::Error {}", traceId, e.getStackTrace());
            throw new BusinessException(ErrorCodes.DB_EXISTING_DATA,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.DB_EXISTING_DATA) + column);
        } catch (Exception e) {
            logger.info("UserService::SaveUser::TraceID {}::Error {}", traceId, e.getStackTrace());
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }


    }

    public String updateUser(Long userId, UserUpdateRequest userUpdateRequest) throws DataIntegrityViolationException{
        String traceId = TraceContext.getTraceId();
        logger.info("UserService::UpdateUser::TraceID {}", traceId);
        try {
            User user = userRepository.findByUserId(userId);
            user = userMapper.mapToUserUpdate(user, userUpdateRequest);
            userRepository.save(user);
            return "User updated successfully";
        } catch (Exception e) {
            logger.info("UserService::SaveUser::TraceID {}::Error {}", traceId, e.getStackTrace());
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }
    }

    public String deleteUser(Long userId) {
        String traceId = TraceContext.getTraceId();
        logger.info("UserService::DeleteUser::TraceID {}", traceId);
        try {
            User user = userRepository.findByUserId(userId);
            user.setDeleted(true);
            userRepository.save(user);
            return "User deleted successfully";
        } catch (Exception e) {
            logger.info("UserService::SaveUser::TraceID {}::Error {}", traceId, e.getStackTrace());
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }
    }
}





