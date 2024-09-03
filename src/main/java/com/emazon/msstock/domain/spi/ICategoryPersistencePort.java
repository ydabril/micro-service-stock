package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;

import java.util.Optional;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    Pagination<Category> getAllCategories(Integer page, Integer size, String sortDirection);
    Optional<Category> findCategoryByName(String name);
    Optional<Category> findCategoryById(Long id);

}
