package com.adwicorp.aanandamsn.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class UpdateUserRequest {
    private String userNameId;

    private String fullName;

    private String email;

    private String imagePath;

    private boolean deleted;
}
