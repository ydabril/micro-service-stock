package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.Constants;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public Pagination<Category> getAllCategories(Integer page, Integer size, String sortDirection) {
        Sort sort = sortDirection.equals(Constants.SORT_DIRECTION_ASC) ? Sort.by(Constants.NAME).ascending() : Sort.by(Constants.NAME).descending();
        Pageable pagination = PageRequest.of(page, size, sort);
        Page<CategoryEntity> categories = categoryRepository.findAll(pagination);

        return new Pagination<>(
                categoryEntityMapper.toModelList(categories.getContent()),
                categories.getNumber(),
                categories.getSize(),
                categories.getTotalElements(),
                categories.getTotalPages(), categories.hasNext(),
                categories.hasPrevious()
        );
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryEntityMapper.toCategoryOptional(categoryRepository.findByName(name));
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryEntityMapper.toCategoryOptional(categoryRepository.findById(id));
    }
}
