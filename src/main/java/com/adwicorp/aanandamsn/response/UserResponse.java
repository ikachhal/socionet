package com.adwicorp.aanandamsn.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private Long userId;

    private String userNameId;

    private String fullName;

    private String email;

    private String imagePath;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}
