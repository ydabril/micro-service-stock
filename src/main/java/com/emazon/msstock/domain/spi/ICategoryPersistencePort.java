package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    List<Category> getAllCategories(Integer page, Integer size, String sortDirection);
    Optional<Category> findCategoryByName(String name);

}