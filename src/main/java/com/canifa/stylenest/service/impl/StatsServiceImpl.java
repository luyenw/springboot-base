package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.Category;
import com.canifa.stylenest.entity.Order;
import com.canifa.stylenest.entity.Stats;
import com.canifa.stylenest.entity.dto.UserDto;
import com.canifa.stylenest.entity.dto.response.ProductResponseDTO;
import com.canifa.stylenest.service.CategoryService;
import com.canifa.stylenest.service.OrderService;
import com.canifa.stylenest.service.ProductService;
import com.canifa.stylenest.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final ProductService productService;
    private final OrderService orderService;
    private final UserDetailsServiceImpl userService;
    private final CategoryService categoryService;

    @Override
    public Stats calculateStats() {
            List<Category> categories = categoryService.getCategoriesByParent("ROOT");

            List<Map<String, Object>> categoryShare = categories.stream()
                    .map(category -> Map.of("category", category, "orders", productService.getProductByCategory(category.getId()).stream().count()))
                    .toList();

            List<Order> orders = orderService.getAll();
            List<ProductResponseDTO> products = productService.getAll();
            List<UserDto> users = userService.getAll();

            long totalRevenue = orders.stream()
                    .mapToLong(Order::getValue)
                    .sum();

            return Stats.builder()
                    .revenue(totalRevenue)
                    .product((long) products.size())
                    .order((long) orders.size())
                    .user((long) users.size())
                    .categoryShare(categoryShare)
                    .build();
    }
}
