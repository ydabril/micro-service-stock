package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Category;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
}
