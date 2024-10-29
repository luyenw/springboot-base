package com.canifa.stylenest.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
    public CommonException() {}
    public CommonException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
