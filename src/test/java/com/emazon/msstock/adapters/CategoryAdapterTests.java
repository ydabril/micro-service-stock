package com.emazon.msstock.adapters;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.emazon.msstock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.Constants;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
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

    @Test
    void testGetAllCategories() {
        int page = 0;
        int size = 2;
        String sortDirection = Constants.SORT_DIRECTION_ASC;

        CategoryEntity entity1 = new CategoryEntity(1L, "Category 1", "Description 1");
        CategoryEntity entity2 = new CategoryEntity(2L, "Category 2", "Description 2");
        List<CategoryEntity> entities = Arrays.asList(entity1, entity2);

        Page<CategoryEntity> categoryPage = new PageImpl<>(entities, PageRequest.of(page, size, Sort.by("name").ascending()), entities.size());

        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(categoryPage);

        Category model1 = new Category(1L, "Category 1", "Description 1");
        Category model2 = new Category(2L, "Category 2", "Description 2");
        List<Category> models = Arrays.asList(model1, model2);
        when(categoryEntityMapper.toModelList(entities)).thenReturn(models);

        Pagination<Category> result = categoryAdapter.getAllCategories(page, size, sortDirection);

        assertNotNull(result);
        assertEquals(page, result.getCurrentPage());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertFalse(result.getList().isEmpty());
        assertEquals(models, result.getList());
        assertFalse(result.isHasNextPage());
        assertFalse(result.isHasPreviousPage());
    }
}