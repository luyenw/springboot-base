package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.Category;
import com.canifa.stylenest.repository.CategoryRepository;
import com.canifa.stylenest.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getCategoriesByParent(String parentId) {
        System.out.println(categoryRepository.findAllByParentId(parentId));
        return categoryRepository.findAllByParentId(parentId);
    }
}
