package com.example.demo.service.implementation;

import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        var existedCategory = categoryRepository.findById(category.getCategoryId()).get();
        existedCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(existedCategory);
    }

    @Override
    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    public void initDb() {
        if (categoryRepository.findAll().stream().noneMatch(c -> "Phone".equalsIgnoreCase(c.getCategoryName()))) {
            Category category1 = new Category();
            category1.setCategoryName("Phone");
            categoryRepository.save(category1);
        }
        if (categoryRepository.findAll().stream().noneMatch(c -> "Tablet".equalsIgnoreCase(c.getCategoryName()))) {
            Category category2 = new Category();
            category2.setCategoryName("Tablet");
            categoryRepository.save(category2);
        }
    }
}
