package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.CartItem;
import com.canifa.stylenest.repository.CartRepository;
import com.canifa.stylenest.repository.OrderRepository;
import com.canifa.stylenest.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    @Override
    public List<CartItem> getItemsByUserId(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public List<CartItem> addItems(Long userId, Map<String, Long> quantityByModelId) {
        cartRepository.deleteAllByUserId(userId);
        return quantityByModelId.entrySet().stream()
                .map(row->  cartRepository.save(
                            CartItem.builder()
                                    .userId(userId)
                                    .modelId(row.getKey())
                                    .quantity(row.getValue())
                                    .build())
                ).toList();
    }

}
