package com.canifa.stylenest.repository;

import com.canifa.stylenest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByIdAndIsDeletedIsFalse(String id);
    List<Product> findByCategoryIdAndIsDeletedIsFalse(String id);
    List<Product> findAllByIsDeletedIsFalse();
}
