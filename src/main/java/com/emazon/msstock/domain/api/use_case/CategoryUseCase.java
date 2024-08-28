package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.exception.CategoryAlreadyExistsException;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {
    private ICategoryPersistencePort categoryPersistencePort;

    public  CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void saveCategory(Category category) {
        if(categoryPersistencePort.findCategoryByName(category.getName()).isPresent()){
            throw new CategoryAlreadyExistsException();
        }

        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public List<Category> getAllCategories(Integer page, Integer size, String sortDirection) {
        return categoryPersistencePort.getAllCategories(page, size, sortDirection);
    }
}
