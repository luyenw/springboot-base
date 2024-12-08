package com.canifa.stylenest.controller;

import com.canifa.stylenest.common.PageRequest;
import com.canifa.stylenest.entity.dto.request.ProductRequestDTO;
import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.exception.BadRequestException;
import com.canifa.stylenest.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;
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

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductByCategory(@PathVariable("id") String id, PageRequest pageRequest) {
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Lấy sản phẩm thành công")
                        .data(productService.getProductByCategory(id, pageRequest))
                        .build()
        );
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestPart("data") String productRequestJsonString, HttpServletRequest request) {
        ProductRequestDTO productRequestDTO = null;
        try {
            productRequestDTO = objectMapper.readValue(productRequestJsonString, ProductRequestDTO.class);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Truyền sản phẩm không đúng định dạng");
        }
        var multipartFileMap = extractRequestFileMap(productRequestDTO, (MultipartHttpServletRequest) request);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Tạo mới sản phẩm thành công")
                        .data(productService.createProduct(productRequestDTO, multipartFileMap))
                        .build()
        );
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                    .success(true)
                    .message("Xóa sản phẩm thành công")
                    .data(id)
                    .build()
        );
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProduct(
            @PathVariable("id") String id,
            @RequestPart("data") ProductRequestDTO productRequestDTO,
            HttpServletRequest request){
        var multipartFileMap = extractRequestFileMap(productRequestDTO, (MultipartHttpServletRequest) request);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Cập nhật sản phẩm thành công")
                        .data(productService.updateProduct(id, productRequestDTO, multipartFileMap))
                        .build()
        );
    }

    private Map extractRequestFileMap(ProductRequestDTO productRequestDTO, MultipartHttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = request;
        var multipartFileMap = new HashMap<>();
        multipartFileMap.put("images", multipartRequest.getFiles("images"));
        productRequestDTO.getModels().stream().forEach(modelRequestDTO -> {
            multipartFileMap.put(modelRequestDTO.getColor(), multipartRequest.getFiles(modelRequestDTO.getColor()));
        });
        return multipartFileMap;
    }
}
