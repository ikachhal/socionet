package com.adwicorp.aanandamsn.controller;

import com.adwicorp.aanandamsn.request.UserRequest;
import com.adwicorp.aanandamsn.response.ApiResponse;
import com.adwicorp.aanandamsn.request.UserUpdateRequest;
import com.adwicorp.aanandamsn.response.UserResponse;
import com.adwicorp.aanandamsn.service.UserService;
import com.adwicorp.aanandamsn.traceconfig.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers(){
        String traceId = TraceContext.getTraceId();
        logger.info("UserController::GetUsers::TraceID {}", traceId);
        List<UserResponse> users = userService.getAllUsers();
        logger.info("Successfully fetched users:: TraceID {}", traceId);
        return ApiResponse.<List<UserResponse>>builder()
                .data(users)
                .message("Data fetched successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping
    public ApiResponse<String> saveUser(@RequestBody UserRequest userRequest) {
        String traceId = TraceContext.getTraceId();
        logger.info("UserController::SaveUser::TraceID {}", traceId);
        String message = userService.saveUser(userRequest);
        logger.info("Successfully saved user::TraceID {}", traceId);
        return ApiResponse.<String>builder()
                .message(message)
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<String> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        String traceId = TraceContext.getTraceId();
        logger.info("UserController::UpdateUser::TraceID {}", traceId);
        String message = userService.updateUser(userId, userUpdateRequest);
        logger.info("Successfully updated user::TraceID {}", traceId);
        return ApiResponse.<String>builder()
                .message(message)
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable("userId") Long userId) {
        String traceId = TraceContext.getTraceId();
        logger.info("UserController::DeleteUser::TraceID {}", traceId);
        String message = userService.deleteUser(userId);
        logger.info("Successfully deleted user:: TraceID {}", traceId);
        return ApiResponse.<String>builder()
                .message(message)
                .status(HttpStatus.OK)
                .build();
    }
}
