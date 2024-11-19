package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.CustomUserDetails;
import com.canifa.stylenest.entity.Order;
import com.canifa.stylenest.entity.OrderItem;
import com.canifa.stylenest.entity.OrderStatus;
import com.canifa.stylenest.entity.dto.request.CheckoutRequest;
import com.canifa.stylenest.repository.ItemRepository;
import com.canifa.stylenest.repository.ModelRepository;
import com.canifa.stylenest.repository.OrderRepository;
import com.canifa.stylenest.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelRepository modelRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<Order> findByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((CustomUserDetails)authentication.getPrincipal()).getId();
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Order checkout(Long userId, CheckoutRequest request) {
        List<OrderItem> items = request.getItems().entrySet().stream().map(
                entry->{
                    String itemId = entry.getKey();
                    Long quantity = entry.getValue();
                    return OrderItem.builder()
                            .modelId(itemId)
                            .model(modelRepository.findById(itemId).get())
                            .quantity(quantity)
                            .userId(userId)
                            .build();
                }
        ).toList();
        Order order = orderRepository.save(Order.builder()
                .name(request.getName())
                .address(request.getAddress())
                .tel(request.getTel())
                .status(OrderStatus.ORDERED)
                .value(LongStream.range(0, items.size()).map(i->items.get((int) i).getModel().getProduct().getPrice()*items.get((int) i).getQuantity()).sum())
                .userId(userId)
                .build());
        items.stream().forEach(item->{
            item.setOrderId(order.getId());
            itemRepository.save(item);
        });
        return order;
    }

    @Override
    public void updateStatus(Long orderId, Integer status) {
        orderRepository.findById(orderId)
                .ifPresentOrElse(
                        order -> order.setStatus(status),
                        ()->{});
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
