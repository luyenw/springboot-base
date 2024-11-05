package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.Product;
import com.canifa.stylenest.entity.dto.request.ProductRequestDTO;
import com.canifa.stylenest.entity.dto.response.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductResponseDTO getProductById(String id);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Map<String, List<MultipartFile>> multipartFileMap);
    Product updateProduct(Product product);
    void deleteProduct(String id);
}
