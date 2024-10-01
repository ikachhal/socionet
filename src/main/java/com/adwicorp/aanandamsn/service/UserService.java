package com.adwicorp.aanandamsn.service;

import com.adwicorp.aanandamsn.entity.User;
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

    public String saveUser(UserRequest userRequest) throws DataIntegrityViolationException{
        User user = userMapper.mapToUser(userRequest);
        userRepository.save(user);
        return "User saved successfully";
    }

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
