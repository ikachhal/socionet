package com.adwicorp.aanandamsn.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {

    @NotNull(message = "User name ID is mandatory.")
    @NotEmpty(message = "User name ID cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_@-]{5,20}$",
            message = "User name ID must contain only alphanumeric characters, @, underscores, or hyphens, and be 5-20 characters long.")
    private String userNameId;

    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters.")
    @Pattern(regexp = "^[a-zA-Z'\\-\\s]+$",
            message = "Full name can only contain alphabetic characters, spaces, hyphens, or apostrophes.")
    @Nullable
    private String fullName;

    @NotNull(message = "Email is mandatory.")
    @Email(message = "Invalid email format.")
    private String email;

    private String imagePath;
}
