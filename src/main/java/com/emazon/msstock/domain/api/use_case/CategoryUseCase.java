package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.exception.CategoryAlreadyExistsException;
import com.emazon.msstock.domain.exception.NoDataFoundException;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;

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
    public Pagination<Category> getAllCategories(Integer page, Integer size, String sortDirection) {
        Pagination<Category> categories = categoryPersistencePort.getAllCategories(page, size, sortDirection);
        if(categories.getList().isEmpty()){
            throw  new NoDataFoundException();
        }

        return categories;
    }
}
