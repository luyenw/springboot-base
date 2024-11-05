package com.canifa.stylenest.repository;

import com.canifa.stylenest.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, String> {
    List<Model> findAllByProductId(String productId);
}
