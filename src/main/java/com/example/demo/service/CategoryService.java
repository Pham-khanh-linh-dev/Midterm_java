package com.example.demo.service;

import com.example.demo.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category save(Category category);
    Category update(Category category);
    void delete(Long categoryId);
    Category getCategoryById(Long categoryId);
    void initDb();
}
