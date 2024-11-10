package com.canifa.stylenest.controller;

import com.canifa.stylenest.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.canifa.stylenest.entity.dto.response.ApiResponse;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getCategory(){
        System.out.println("get category");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                .success(true)
                .data(categoryService.getCategoriesByParent("ROOT")).build()
        );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") String parentCategory){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .success(true)
                        .data(categoryService.getCategoriesByParent(parentCategory)).build()
        );
    }
}
