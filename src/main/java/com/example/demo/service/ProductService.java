package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void save(Product product, MultipartFile file) throws IOException;
    Product update(Product product, MultipartFile file) throws IOException;
    void delete(Long id);
    List<Product> getAllProducts();
    List<Category> getAllCategories();
    Product getById(Long id);
    List<Product> getSortedProducts(boolean ascending);
    List<Product> getProductsByCategory(Category category);
    List<Product> findRelatedProduct(Product product);
    void initDB();
}
