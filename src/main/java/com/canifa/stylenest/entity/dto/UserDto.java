package com.canifa.stylenest.entity.dto;

import com.canifa.stylenest.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private List<String> roles;
}
