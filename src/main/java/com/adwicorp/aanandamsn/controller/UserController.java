package com.adwicorp.aanandamsn.controller;

import com.adwicorp.aanandamsn.model.request.UpdateUserRequest;
import com.adwicorp.aanandamsn.model.request.UserRequest;
import com.adwicorp.aanandamsn.model.response.ApiResponse;
import com.adwicorp.aanandamsn.model.response.UserResponse;
import com.adwicorp.aanandamsn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "Fetch all users", description = "Retrieve a list of all users.")
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers(){
        logger.info("Save user request received");
        List<UserResponse> users = userService.getAllUsers();
        logger.info("Save user request fulfilled");
        return ApiResponse.<List<UserResponse>>builder()
                .data(users)
                .message("Data fetched successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Save a user", description = "Add a new user to the system.")
    @PostMapping
    public ApiResponse<String> saveUser(@RequestBody UserRequest userRequest) {
        logger.info("Save user request received");
        String message = userService.saveUser(userRequest);
        logger.info("Save user request fulfilled");
        return ApiResponse.<String>builder()
                .message(message)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Update a user", description = "Update an existing user by ID.")
    @PutMapping("/{userId}")
    public ApiResponse<String> updateUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequest updateUserRequest) {
        logger.info("Save user request received");
        String message = userService.updateUser(userId, updateUserRequest);
        logger.info("Save user request fulfilled");
        return ApiResponse.<String>builder()
                .message(message)
                .status(HttpStatus.OK)
                .build();
    }
}
