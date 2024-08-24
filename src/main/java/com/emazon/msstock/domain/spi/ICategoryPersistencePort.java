package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    List<Category> getAllCategories(Integer page, Integer size, String sortDirection);
}
