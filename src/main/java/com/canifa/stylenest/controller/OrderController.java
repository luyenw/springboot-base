package com.canifa.stylenest.controller;

import com.canifa.stylenest.entity.CustomUserDetails;
import com.canifa.stylenest.entity.dto.request.CheckoutRequest;
import com.canifa.stylenest.entity.dto.request.UpdateOrderStatusRequest;
import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .success(true)
                        .data(orderService.getAll())
                        .build());
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> getAllByUser(){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                .success(true)
                .data(orderService.findByUserId())
                .build());
    }

    @PostMapping("/checkout")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest req){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .success(true)
                        .build()
        );
    }
}
