package com.canifa.stylenest.entity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {
    @NotEmpty(message = "Không được bỏ trống username")
    private String username;
    @NotEmpty(message = "Không được bỏ trống password")
    private String password;
}
