package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;

public interface ICategoryServicePort {
    void saveCategory(Category category);
    Pagination<Category> getAllCategories(Integer page, Integer size, String sortDirection);
}
