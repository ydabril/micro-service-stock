package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Category;
import java.util.List;

public interface ICategoryServicePort {
    void saveCategory(Category category);
    List<Category> getAllCategories(Integer page, Integer size, String sortDirection);
}
