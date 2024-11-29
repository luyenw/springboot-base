package com.canifa.stylenest.controller;

import com.canifa.stylenest.entity.CustomUserDetails;
import com.canifa.stylenest.entity.dto.request.CartRequest;
import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> getCurrentCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((CustomUserDetails)authentication.getPrincipal()).getId();
        System.out.println("user id "+userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .data(cartService.getItemsByUserId(userId))
                        .success(true)
                        .build()
        );
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> addItem(@RequestBody @Valid CartRequest cartRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((CustomUserDetails)authentication.getPrincipal()).getId();
        cartService.addItems(userId, cartRequest.getItems());
        return getCurrentCart();
    }
}
