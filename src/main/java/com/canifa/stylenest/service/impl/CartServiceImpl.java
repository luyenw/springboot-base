package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.CartItem;
import com.canifa.stylenest.repository.CartRepository;
import com.canifa.stylenest.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    @Override
    public List<CartItem> getItemsByUserId(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    @Override
    public List<CartItem> addItems(Long userId, Map<String, Long> quantityByModelId) {
        quantityByModelId.entrySet().stream()
                .forEach(row->
                        {
                            cartRepository.findByUserIdAndModelId(userId, row.getKey())
                            .ifPresentOrElse(
                                    item->item.setQuantity(row.getValue()),
                                    ()-> {
                                        cartRepository.save(
                                                CartItem.builder()
                                                        .userId(userId)
                                                        .modelId(row.getKey())
                                                        .quantity(row.getValue())
                                                        .build());
                                    });
                        });
        return getItemsByUserId(userId);
    }
}
