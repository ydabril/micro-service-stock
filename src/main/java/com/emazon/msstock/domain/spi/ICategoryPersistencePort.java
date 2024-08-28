package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Category;

import java.util.Optional;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    Optional<Category> findCategoryByName(String name);
}
