package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.msstock.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.Constants;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public void saveCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException();
        }

        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public List<Category> getAllCategories(Integer page, Integer size, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pagination = PageRequest.of(page, size, Sort.by(direction, Constants.categoryField.name.toString()));
        List<CategoryEntity> categories = categoryRepository.findAll(pagination).getContent();
        if (categories.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toModelList(categories);
    }
}
