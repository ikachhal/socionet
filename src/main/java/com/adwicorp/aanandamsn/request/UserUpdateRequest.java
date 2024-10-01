package com.adwicorp.aanandamsn.request;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private String userNameId;

    private String fullName;

    private String email;

    private String imagePath;
}
