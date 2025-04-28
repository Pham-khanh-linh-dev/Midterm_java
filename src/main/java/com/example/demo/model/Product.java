package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private double price;
    private String brand;
    private String fileUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    private MultipartFile imageFile;

    public Product(){
        super();
    }


    public Product(String name, String description, double price, String brand, String fileUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.fileUrl = fileUrl;
    }

    public Product(String name, String description, double price, String brand, String fileUrl, Category category, MultipartFile imageFile) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.fileUrl = fileUrl;
        this.category = category;
        this.imageFile = imageFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
