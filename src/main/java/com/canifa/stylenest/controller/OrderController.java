package com.canifa.stylenest.controller;

import com.canifa.stylenest.entity.CustomUserDetails;
import com.canifa.stylenest.entity.dto.request.CheckoutRequest;
import com.canifa.stylenest.entity.dto.request.UpdateOrderStatusRequest;
import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/all")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .success(true)
                        .data(orderService.getAll())
                        .build());
    }

    @GetMapping("/")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> getAllByUser(){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                .success(true)
                .data(orderService.findByUserId())
                .build());
    }

    @PostMapping("/checkout")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest checkoutRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((CustomUserDetails)authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .data(orderService.checkout(userId, checkoutRequest))
                        .success(true)
                        .build()
        );
    }

    @PatchMapping("/{id}/status")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest req){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .success(true)
                        .build()
        );
    }
}
