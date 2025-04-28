package com.example.demo.repositories;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findByCategory(Category category);
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.id <> :productId")
    List<Product> findRelatedProductsByCategoryAndExcludeProduct(
            @Param("categoryId") Long categoryId,
            @Param("productId") Long productId
    );
    @Query("SELECT DISTINCT p.category FROM Product p")
    List<Category> findAllCategories();
}
