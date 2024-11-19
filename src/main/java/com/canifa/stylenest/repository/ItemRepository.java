package com.canifa.stylenest.repository;

import com.canifa.stylenest.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<OrderItem, Long> {
}
