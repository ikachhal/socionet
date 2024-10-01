package com.adwicorp.aanandamsn.controller;

import com.adwicorp.aanandamsn.request.UserRequest;
import com.adwicorp.aanandamsn.response.ApiResponse;
import com.adwicorp.aanandamsn.request.UserUpdateRequest;
import com.adwicorp.aanandamsn.response.UserResponse;
import com.adwicorp.aanandamsn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers(){
        try {
            List<UserResponse> users = userService.getAllUsers();
            return ApiResponse.<List<UserResponse>>builder()
                    .data(users)
                    .message("Data fetched successfully")
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<UserResponse>>builder()
                    .errorDetails(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping
    public ApiResponse<String> saveUser(@RequestBody UserRequest userRequest) {
        try {
            String message = userService.saveUser(userRequest);
            return ApiResponse.<String>builder()
                    .message(message)
                    .status(HttpStatus.OK)
                    .build();
        } catch (DataIntegrityViolationException e) {
            return ApiResponse.<String>builder()
                    .errorDetails(e.getMessage())
                    .status(HttpStatus.CONFLICT)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .errorDetails("An unexpected error occurred: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PutMapping("/{userId}")
    public ApiResponse<String> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            String message = userService.updateUser(userId, userUpdateRequest);
            return ApiResponse.<String>builder()
                    .message(message)
                    .status(HttpStatus.OK)
                    .build();
        } catch (DataIntegrityViolationException e) {
            return ApiResponse.<String>builder()
                    .errorDetails(e.getMessage())
                    .status(HttpStatus.CONFLICT)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .errorDetails("An unexpected error occurred: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable("userId") Long userId) {
        try {
            String message = userService.deleteUser(userId);
            return ApiResponse.<String>builder()
                    .message(message)
                    .status(HttpStatus.OK)
                    .build();
        } catch (DataIntegrityViolationException e) {
            return ApiResponse.<String>builder()
                    .errorDetails(e.getMessage())
                    .status(HttpStatus.CONFLICT)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .errorDetails("An unexpected error occurred: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
