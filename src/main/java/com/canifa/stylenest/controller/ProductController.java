package com.canifa.stylenest.controller;

import com.canifa.stylenest.config.PathRouting;
import com.canifa.stylenest.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private PathRouting pathRouting;
    @GetMapping("")
    public ResponseEntity getProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll(page, size));
    }

    @GetMapping("/{category}/{sub}")
    public ResponseEntity getCategoryProducts(
            @PathVariable(name = "category", required = true) String category,
            @PathVariable(name = "sub", required = false) String sub,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        String fullPath = category + "/" + sub;
        Long categoryId = pathRouting.getCategoryId(fullPath);
        if (categoryId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getAll(page, size));
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.getByCategory(page, size, categoryId));
    }

}
