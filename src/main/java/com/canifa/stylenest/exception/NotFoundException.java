package com.canifa.stylenest.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CommonException {
    public NotFoundException(String message) {
        this.setMessage(message);
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
