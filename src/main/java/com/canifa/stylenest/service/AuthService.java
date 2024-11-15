package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.dto.response.AuthResponse;

import java.util.Set;

public interface AuthService {
    AuthResponse login(String username, String password);
    void register(String username, String password, Set<String> roles);
}
