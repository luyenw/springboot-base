package com.canifa.stylenest.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthRequest {
    @NotEmpty(message = "Không được bỏ trống username")
    private String username;
    @NotEmpty(message = "Không được bỏ trống password")
    private String password;
}
