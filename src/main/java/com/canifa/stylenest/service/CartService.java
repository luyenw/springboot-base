package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    List<CartItem> getItemsByUserId(Long userId);
    List<CartItem> addItems(Long userId, Map<String, Long> quantityByItemId);
}
