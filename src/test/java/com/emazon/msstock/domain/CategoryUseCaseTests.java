package com.emazon.msstock.domain;

import com.emazon.msstock.domain.api.use_case.CategoryUseCase;
import com.emazon.msstock.domain.exception.*;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryUseCaseTests {

    @Mock
    private ICategoryPersistencePort persistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategorySuccessfully() {
        String validName = "NewCategory";
        String validDescription = "Valid description";
        Category category = new Category(1L, validName, validDescription);

        when(persistencePort.findCategoryByName(validName)).thenReturn(Optional.empty());

        categoryUseCase.saveCategory(category);

        verify(persistencePort, times(1)).saveCategory(any(Category.class));
    }

    @Test
    void testSaveCategoryWithLongNameShouldFail() {
        String longName = "ThisCategoryNameIsWayTooLongAndShouldCauseAValidationError";
        String description = "Valid description";

        assertThrows(LengthFieldException.class, () -> {
            categoryUseCase.saveCategory(new Category(1L, longName, description));
        });
    }

    @Test
    void testSaveCategoryWithLongDescriptionShouldFail() {
        String name = "ValidName";
        String longDescription = "This description is way too long and should cause a validation error because it exceeds the maximum length allowed by the application rules.";

        assertThrows(LengthFieldException.class, () -> {
            categoryUseCase.saveCategory(new Category(1L, name, longDescription));
        });
    }

    @Test
    void testSaveCategoryWithEmptyNameShouldFail() {
        String emptyName = "";
        String description = "Valid description";

        assertThrows(EmptyFieldException.class, () -> {
            categoryUseCase.saveCategory(new Category(1L, emptyName, description));
        });
    }

    @Test
    void testSaveCategoryWithEmptyDescriptionShouldFail() {
        String name = "ValidName";
        String emptyDescription = "";

        assertThrows(EmptyFieldException.class, () -> {
            categoryUseCase.saveCategory(new Category(1L, name, emptyDescription));
        });
    }
    @Test
    void testSaveCategoryWithNullNameShouldFail() {
        String nullName = null;
        String description = "Valid description";

        assertThrows(EmptyFieldException.class, () -> {
            categoryUseCase.saveCategory(new Category(1L, nullName, description));
        });
    }

    @Test
    void testSaveCategoryWithNullDescriptionShouldFail() {
        String name = "ValidName";
        String nullDescription = null;

        assertThrows(EmptyFieldException.class, () -> {
            categoryUseCase.saveCategory(new Category(1L, name, nullDescription));
        });
    }


    @Test
    void testSaveCategoryThatAlreadyExistsShouldFail() {
        String name = "ExistingCategory";
        String description = "Valid description";
        Category category = new Category(1L, name, description);

        when(persistencePort.findCategoryByName(name)).thenReturn(Optional.of(category));

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    public void testGetAllCategoriess() {
        Pagination<Category> pagination = new Pagination<>(
                Collections.singletonList(new Category(1L,"Category1", "Description1")),
                0,
                10,
                1,
                1,
                false,
                false
        );
        when(persistencePort.getAllCategories(anyInt(), anyInt(), anyString())).thenReturn(pagination);

        Pagination<Category> result = categoryUseCase.getAllCategories(0, 10, "asc");

        assertNotNull(result);
        assertFalse(result.getList().isEmpty());
        assertEquals("Category1", result.getList().get(0).getName());
    }
}

