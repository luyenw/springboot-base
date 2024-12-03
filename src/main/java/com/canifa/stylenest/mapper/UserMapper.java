package com.canifa.stylenest.mapper;

import com.canifa.stylenest.entity.User;
import com.canifa.stylenest.entity.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(role->role.toString()).toList())
                .build();
    }

    public List<UserDto> toUserDTOs(List<User> users) {
        return users.stream()
                .map(this::toUserDTO).toList();
    }
}

