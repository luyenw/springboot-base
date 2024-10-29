package com.canifa.stylenest.exception;

import com.canifa.stylenest.entity.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestControlExceptionHandle {
    @ExceptionHandler({CommonException.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<?>> resolveCommonException(CommonException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.builder()
                        .message(e.getMessage())
                        .success(false)
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<?>> resolveInvalidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .message(e.getAllErrors().get(0).getDefaultMessage())
                        .success(false)
                        .build());
    }
}
