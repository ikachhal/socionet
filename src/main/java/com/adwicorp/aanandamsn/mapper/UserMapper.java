package com.adwicorp.aanandamsn.mapper;

import com.adwicorp.aanandamsn.entity.User;
import com.adwicorp.aanandamsn.request.UserRequest;
import com.adwicorp.aanandamsn.response.UserResponse;
import com.adwicorp.aanandamsn.request.UserUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserRequest userRequest) {
        return User.builder()
                .userNameId(userRequest.getUserNameId())
                .fullName(userRequest.getFullName())
                .email(userRequest.getEmail())
                .build();
    }

    public User mapToUserUpdate(User user, UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.getEmail() != null){
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getFullName() != null) {
            user.setFullName(userUpdateRequest.getFullName());
        }
        if (userUpdateRequest.getUserNameId() != null) {
            user.setUserNameId(userUpdateRequest.getUserNameId());
        }
        if(userUpdateRequest.getImagePath() != null) {
            user.setImagePath(userUpdateRequest.getImagePath());
        }
        return user;
    }

    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userNameId(user.getUserNameId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .build();
    }
}
