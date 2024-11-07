package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getCategoriesByParent(String parenId);
}
