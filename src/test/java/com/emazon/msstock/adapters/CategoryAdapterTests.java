package com.emazon.msstock.adapters;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.emazon.msstock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msstock.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CategoryAdapterTests {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;
    @InjectMocks
    private CategoryAdapter categoryAdapter;
    private Category category;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(1L, "Valid Name", "Valid Description");
        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Valid Name");
        categoryEntity.setDescription("Valid Description");
    }

    @Test
    void testSaveCategory() {
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.saveCategory(category);

        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    void testFindCategoryByName() {
        String name = "Valid Name";
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);

        when(categoryRepository.findByName(name)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategoryOptional(Optional.of(categoryEntity)))
                .thenReturn(Optional.of(new Category(1L, name, "Valid Description")));

        Optional<Category> result = categoryAdapter.findCategoryByName(name);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());

        verify(categoryRepository).findByName(name);
    }
}