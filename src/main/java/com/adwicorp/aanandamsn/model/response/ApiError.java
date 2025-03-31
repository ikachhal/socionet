package com.adwicorp.aanandamsn.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private String errorCode;
    private String message;

    public ApiError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
