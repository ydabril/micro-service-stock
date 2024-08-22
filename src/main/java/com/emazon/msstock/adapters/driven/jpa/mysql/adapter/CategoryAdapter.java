package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

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
}
