package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.Role;
import com.canifa.stylenest.entity.User;
import com.canifa.stylenest.entity.dto.response.AuthResponse;
import com.canifa.stylenest.repository.RoleRepository;
import com.canifa.stylenest.repository.UserRepository;
import com.canifa.stylenest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public AuthResponse login(String username, String password) {
        return null;
    }

    @Override
    public void register(String username, String password, Set<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Hash mật khẩu

        // Thêm role cho user
        Set<Role> userRoles = roles.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
        user.setRoles(userRoles);

        userRepository.save(user);
    }
}
