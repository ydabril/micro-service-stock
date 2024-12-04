package com.emazon.msstock.domain.model;

import java.math.BigDecimal;
import java.util.List;

public class Article {
    private final Long id;
    private final String name;

    private final String description;
    private final BigDecimal price;
    private Long quantity;
    private Brand brand;
    private List<Category> categories;

    private String imagePath;

    public Article(Long id, String name, String description, BigDecimal price, Long quantity, Brand brand, List<Category> categories, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.categories = categories;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    public Long getQuantity() {
        return quantity;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
