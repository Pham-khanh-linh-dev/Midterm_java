package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin-page/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void seedCategory(){
        categoryService.initDb();
    }
    @GetMapping
    public String getCategoryPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories_list";
    }

    @GetMapping("/add")
    public String showCategoryForm(Model model){
        model.addAttribute("categories", new Category());
        return "categories_add";
    }

    @PostMapping("/add")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin-page/categories";
    }

    @GetMapping("/edit/{categoryId}")
    public String showEditCategory(@PathVariable Long categoryId, Model model){
        Category category =  categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        return "categories_edit";
    }

    @PostMapping("/edit/{categoryId}")
    public String updateCategory(@PathVariable Long categoryId, @ModelAttribute Category category) {
        category.setCategoryId(categoryId);
        categoryService.update(category);
        return "redirect:/admin-page/categories";
    }

    @PostMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return "redirect:/admin-page/categories";
    }
}
