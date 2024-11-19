package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.Order;
import com.canifa.stylenest.entity.dto.request.CheckoutRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> findByUserId();
    Order checkout(Long userId, CheckoutRequest checkoutRequest);
    void updateStatus(Long orderId, Integer status);
    List<Order> getAll();
}
