package com.canifa.stylenest.repository;

import com.canifa.stylenest.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    @Query(value = "SELECT DISTINCT files.id, files.name FROM files INNER JOIN media ON files.id=media.file_id INNER JOIN products ON products.id = media.product_id WHERE products.id = :productId", nativeQuery = true)
    List<File> findAllByProductId(String productId);
    @Query(value = "SELECT files.id, files.name FROM files INNER JOIN media ON files.id=media.file_id INNER JOIN models ON models.id = media.model_id WHERE models.id = :modelId", nativeQuery = true)
    List<File> findAllByModelId(String modelId);
}
