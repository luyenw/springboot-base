package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.dto.response.ModelResponseDTO;

import java.util.List;

public interface ModelService {
    List<ModelResponseDTO> findAllByProductId(String productId);
}
