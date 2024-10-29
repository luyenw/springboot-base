package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.Product;
import com.canifa.stylenest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAll(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAll(pageable);
    }

    public Page<Product> getByCategory(int page, int pageSize, Long category){
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAllByCategoryIdsInAndVisibilityIn(List.of(category), List.of(2L, 3L, 4L), pageable);
    }
}
