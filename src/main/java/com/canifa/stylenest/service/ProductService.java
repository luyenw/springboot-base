package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.Product;
import com.canifa.stylenest.entity.dto.request.ProductRequestDTO;
import com.canifa.stylenest.entity.dto.response.ProductResponseDTO;
import com.canifa.stylenest.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductResponseDTO getProductById(String id) throws NotFoundException;
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Map<String, List<MultipartFile>> multipartFileMap);
    ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO, Map<String, List<MultipartFile>> multipartFileMap) throws NotFoundException;
    void deleteProduct(String id);
}
