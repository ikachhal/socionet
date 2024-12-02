package com.adwicorp.aanandamsn.mapper;

import com.adwicorp.aanandamsn.model.entity.UserEntity;
import com.adwicorp.aanandamsn.model.request.UpdateUserRequest;
import com.adwicorp.aanandamsn.model.request.UserRequest;
import com.adwicorp.aanandamsn.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity mapToUser(UserRequest userRequest) {
        return UserEntity.builder()
                .userNameId(userRequest.getUserNameId())
                .fullName(userRequest.getFullName())
                .email(userRequest.getEmail())
                .build();
    }

    public UserEntity mapToUserUpdate(UserEntity user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getEmail() != null){
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getFullName() != null) {
            user.setFullName(updateUserRequest.getFullName());
        }
        if (updateUserRequest.getUserNameId() != null) {
            user.setUserNameId(updateUserRequest.getUserNameId());
        }
        if(updateUserRequest.getImagePath() != null) {
            user.setImagePath(updateUserRequest.getImagePath());
        }
        if(updateUserRequest.isDeleted()) {
            user.setDeleted(updateUserRequest.isDeleted());
        }
        return user;
    }

    public UserResponse mapToUserResponse(UserEntity user) {
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
