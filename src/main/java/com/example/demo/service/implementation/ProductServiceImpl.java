package com.example.demo.service.implementation;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;
    @Override
    public void save(Product product, MultipartFile file) throws IOException{
        saveImage(product, file);
        productRepository.save(product);
    }

    @Override
    public Product update(Product product, MultipartFile file) throws IOException {
        var existedProduct = productRepository.findById(product.getId()).get();
        existedProduct.setName(product.getName());
        existedProduct.setBrand(product.getBrand());
        existedProduct.setDescription(product.getDescription());
        existedProduct.setPrice(product.getPrice());
        existedProduct.setCategory(product.getCategory());
        if(file != null) {
            saveImage(existedProduct, file);
        }
        return productRepository.save(existedProduct);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Category> getAllCategories() {
        return productRepository.findAllCategories();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getSortedProducts(boolean ascending) {
        if(ascending)
            return productRepository.findAllByOrderByPriceAsc();
        return productRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findRelatedProduct(Product product) {
        return productRepository.findRelatedProductsByCategoryAndExcludeProduct(
                product.getCategory().getCategoryId(),
                product.getId()
        );
    }

    @Override
    public void initDB() {
        Category category1 = new Category(1L);

        Category category2 = new Category(2L);

        Product product1 = new Product();
        product1.setName("Iphone 15");
        product1.setPrice(799.0);
        product1.setBrand("Apple");
        product1.setDescription("Newest Phone");
        product1.setCategory(category1);
        product1.setFileUrl("../uploads/iphone15.jpg");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Ipad Air 4");
        product2.setPrice(599.0);
        product2.setBrand("Apple");
        product2.setDescription("Newest Tablet");
        product2.setCategory(category2);
        product2.setFileUrl("../uploads/ipadair4.png");
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Ipad Gen 10");
        product3.setPrice(699.0);
        product3.setBrand("Apple");
        product3.setDescription("Newest Tablet");
        product3.setCategory(category2);
        product3.setFileUrl("../uploads/ipadgen10.jpg");
        productRepository.save(product3);


        Product product4 = new Product();
        product4.setName("Ipad Mini");
        product4.setPrice(699.0);
        product4.setBrand("Apple");
        product4.setDescription("Newest Tablet");
        product4.setCategory(category2);
        product4.setFileUrl("../uploads/ipadmini.jpg");
        productRepository.save(product4);

        Product product5 = new Product();
        product5.setName("Samsung S23");
        product5.setPrice(849.0);
        product5.setBrand("Samsung");
        product5.setDescription("Newest Phone");
        product5.setCategory(category1);
        product5.setFileUrl("../uploads/samsungs23.png");
        productRepository.save(product5);

        Product product6 = new Product();
        product6.setName("Iphone 15 Pro Max");
        product6.setPrice(999.0);
        product6.setBrand("Apple");
        product6.setDescription("Newest Phone");
        product6.setCategory(category1);
        product6.setFileUrl("../uploads/iphone15promax.png");
        productRepository.save(product6);

        Product product7 = new Product();
        product7.setName("Samsung S10");
        product7.setPrice(499.0);
        product7.setBrand("Samsung");
        product7.setDescription("Newest Phone");
        product7.setCategory(category1);
        product7.setFileUrl("../uploads/samsungs10.jpg");
        productRepository.save(product7);

        Product product8 = new Product();
        product8.setName("Ipad Pro 11");
        product8.setPrice(749.0);
        product8.setBrand("Apple");
        product8.setDescription("Newest Tablet");
        product8.setCategory(category2);
        product8.setFileUrl("../uploads/ipadpro11.png");
        productRepository.save(product8);
    }

    private void saveImage(Product product, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // Save the image file to the local directory
            String filename = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, filename);
            Files.write(filePath, file.getBytes());

            product.setFileUrl("../uploads/" + filename);
        }
    }
}
