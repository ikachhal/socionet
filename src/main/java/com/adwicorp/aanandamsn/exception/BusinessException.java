package com.adwicorp.aanandamsn.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private final String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
