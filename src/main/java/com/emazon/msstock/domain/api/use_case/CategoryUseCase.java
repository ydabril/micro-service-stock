package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.exception.CategoryAlreadyExistsException;
import com.emazon.msstock.domain.exception.EmptyFieldException;
import com.emazon.msstock.domain.exception.LengthFieldException;
import com.emazon.msstock.domain.exception.NoDataFoundException;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import com.emazon.msstock.domain.util.DomainConstants;

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

        if(category.getName() == null || category.getName().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if(category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if(category.getName().length() > DomainConstants.MAXIMUM_LENGTH_NAME) {
            throw new LengthFieldException(DomainConstants.Field.NAME.toString());
        }

        if(category.getDescription().length() > DomainConstants.MAXIMUM_LENGTH_DESCRIPTION) {
            throw new LengthFieldException(DomainConstants.Field.DESCRIPTION.toString());
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
