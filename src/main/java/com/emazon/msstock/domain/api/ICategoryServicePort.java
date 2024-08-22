package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Category;

public interface ICategoryServicePort {
    void saveCategory(Category category);
}
