package com.adwicorp.aanandamsn.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {

    @NotNull(message = "User name ID is mandatory.")
    @NotEmpty(message = "User name ID cannot be empty.")
    private String userNameId;

    private String fullName;

    @NotNull(message = "Email is mandatory.")
    @Email(message = "Invalid email format.")
    private String email;

    private String imagePath;
}
