package com.canifa.stylenest.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CommonException {
    public BadRequestException(String message) {
        this.setMessage(message);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
