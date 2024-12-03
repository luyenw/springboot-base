package com.canifa.stylenest.controller;

import com.canifa.stylenest.common.PageRequest;
import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.service.ProductService;
import com.canifa.stylenest.service.StatsService;
import com.canifa.stylenest.service.impl.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final StatsService statsService;
    private final UserDetailsServiceImpl userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> admin(){
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getStats(){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .success(true)
                .data(statsService.calculateStats())
                .build()
        );
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getUsers(@RequestBody @Valid PageRequest pageRequest){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .success(true)
                .data(userService.getAll(pageRequest))
                .build()
        );
    }
}
