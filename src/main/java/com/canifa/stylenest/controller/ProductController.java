package com.canifa.stylenest.controller;

import com.canifa.stylenest.entity.Product;
import com.canifa.stylenest.entity.dto.request.ModelRequestDTO;
import com.canifa.stylenest.entity.dto.request.ProductRequestDTO;
import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Lấy sản phẩm thành công")
                        .data(productService.getProductById(id))
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestPart("data") ProductRequestDTO productRequestDTO, HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, List<MultipartFile>> multipartFileMap = new HashMap<>();
        multipartFileMap.put("images", multipartRequest.getFiles("images"));
        for(ModelRequestDTO modelRequestDTO : productRequestDTO.getModels()){
            multipartFileMap.put(modelRequestDTO.getColor(), multipartRequest.getFiles(modelRequestDTO.getColor()));
        }
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Tạo mới sản phẩm thành công")
                        .data(productService.createProduct(productRequestDTO, multipartFileMap))
                        .build()
        );
    }
}
