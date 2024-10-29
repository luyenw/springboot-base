package com.canifa.stylenest.repository;

import com.canifa.stylenest.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    public Page<Product> findAllByCategoryIdsInAndVisibilityIn(List<Long> categoryIds, List<Long> visibility, Pageable pageable);
}
