package com.emazon.msstock.domain.model;

import com.emazon.msstock.domain.exception.EmptyFieldException;
import com.emazon.msstock.domain.exception.NegativeNotAllowedException;
import com.emazon.msstock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Article {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final Long quantity;
    private Brand brand;
    private List<Category> categories ;

    public Article(long id, String name, BigDecimal price, Long quantity, Brand brand, List<Category> categories) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeNotAllowedException(DomainConstants.FieldArticle.PRICE.toString());
        }
        if (quantity < 0) {
            throw new NegativeNotAllowedException(DomainConstants.FieldArticle.QUANTITY.toString());
        }

        if(categories == null || categories.isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FieldArticle.CATEGORIES.toString());
        }

        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.price = requireNonNull(price, DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        this.quantity = requireNonNull(quantity, DomainConstants.FIELD_QUANTITY_NULL_MESSAGE);
        this.brand = requireNonNull(brand, DomainConstants.FIELD_BRAND_NULL_MESSAGE);
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
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
}