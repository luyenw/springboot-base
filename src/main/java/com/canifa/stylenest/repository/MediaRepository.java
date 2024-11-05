package com.canifa.stylenest.repository;

import com.canifa.stylenest.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    List<Media> findAllByProductId(String title);
}
