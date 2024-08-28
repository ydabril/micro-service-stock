package com.emazon.msstock.domain;

import com.emazon.msstock.domain.api.use_case.CategoryUseCase;
import com.emazon.msstock.domain.exception.*;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

        assertThrows(LengthFieldException.class, () -> new Category(1L, longName, description));
    }

    @Test
    void testSaveCategoryWithLongDescriptionShouldFail() {
        String name = "ValidName";
        String longDescription = "This description is way too long and should cause a validation error because it exceeds the maximum length allowed by the application rules.";

        assertThrows(LengthFieldException.class, () -> new Category(1L, name, longDescription));
    }

    @Test
    void testSaveCategoryWithEmptyNameShouldFail() {
        String emptyName = "";
        String description = "Valid description";

        assertThrows(EmptyFieldException.class, () -> new Category(1L, emptyName, description));
    }

    @Test
    void testSaveCategoryWithEmptyDescriptionShouldFail() {
        String name = "ValidName";
        String emptyDescription = "";

        assertThrows(EmptyFieldException.class, () -> new Category(1L, name, emptyDescription));
    }

    @Test
    void testSaveCategoryThatAlreadyExistsShouldFail() {
        String name = "ExistingCategory";
        String description = "Valid description";
        Category category = new Category(1L, name, description);

        when(persistencePort.findCategoryByName(name)).thenReturn(Optional.of(category));

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.saveCategory(category));
    }
}

