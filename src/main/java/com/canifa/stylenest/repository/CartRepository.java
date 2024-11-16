package com.canifa.stylenest.repository;

import com.canifa.stylenest.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByUserId(Long Id);
    Optional<CartItem> findByUserIdAndModelId(Long userId, String modelId);
}
