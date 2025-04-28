package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class DashboardController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/dashboard")
    public String showMainPage(Model model){
        List<Category> categories = categoryService.getAllCategories();
        Map<Category, List<Product>> productsInCategory = new HashMap<>();
        for(Category category: categories){
            List<Product> productsByCategory = productService.getProductsByCategory(category);
            productsInCategory.put(category, productsByCategory);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("productsInCategory", productsInCategory);
        return "dashboard";
    }

    @GetMapping("all-product")
    public String showAllProduct(Model model,
                                 @RequestParam(name = "category", required = false) Long categoryId,
                                 @RequestParam(name = "sortBy", defaultValue = "") String sortBy){
        List<Category> categories = categoryService.getAllCategories();
        List<Product> allProducts = productService.getAllProducts();
        List<Product> filteredProducts = new ArrayList<>(allProducts);
        if(categoryId != null){
            Category category = categoryService.getCategoryById(categoryId.longValue());
            if (category != null) {
                filteredProducts.retainAll(productService.getProductsByCategory(category));
            }
        }

        switch (sortBy){
            case "desc":
                Collections.sort(filteredProducts, Comparator.comparing(Product::getPrice).reversed());
                break;
            case "asc":
                Collections.sort(filteredProducts, Comparator.comparing(Product::getPrice));
                break;
        }
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("categories", categories);
        model.addAttribute("products", filteredProducts);
        return "all-product";
    }

    @GetMapping("detail/{id}")
    public String showDetailPage(@PathVariable Long id, Model model){
        Product product = productService.getById(id);
        List<Product> relatedProduct = productService.findRelatedProduct(product);
        model.addAttribute("detailProduct", product);
        model.addAttribute("relatedProduct", relatedProduct);
        return "product_detail";
    }
}
