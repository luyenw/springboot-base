package com.canifa.stylenest.controller;
import com.canifa.stylenest.entity.User;
import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.entity.dto.request.AuthRequest;
import com.canifa.stylenest.entity.dto.response.AuthResponse;
import com.canifa.stylenest.exception.CommonException;
import com.canifa.stylenest.service.UserService;
import com.canifa.stylenest.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody AuthRequest authRequest) {
        User existingUser = userService.findByUsername(authRequest.getUsername());
        if (existingUser != null) {
            throw new CommonException("username đã tồn tại", HttpStatus.CONFLICT);
        }

        userService.registerUser(authRequest.getUsername(), authRequest.getPassword(), Set.of("USER"));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder().success(true).message("Đăng ký thành công").build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .data(new AuthResponse(jwtUtil.generateToken((UserDetails) authentication.getPrincipal())))
                            .success(true)
                            .message("Đăng nhập thành công")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder().success(false).message("Login failed: " + e.getMessage()).build());
        }
    }
}

