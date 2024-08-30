package com.emazon.msstock.domain;

import com.emazon.msstock.domain.api.use_case.BrandUseCase;
import com.emazon.msstock.domain.exception.BrandAlreadyExistsException;
import com.emazon.msstock.domain.exception.EmptyFieldException;
import com.emazon.msstock.domain.exception.LengthFieldException;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
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
import static org.mockito.Mockito.when;

public class BrandUseCaseTests {
    @Mock
    private IBrandPersistencePort persistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBrandSuccessfully() {
        String validName = "NewBrand";
        String validDescription = "Valid description";
        Brand brand = new Brand(1L, validName, validDescription);

        when(persistencePort.findBrandByName(validName)).thenReturn(Optional.empty());

        brandUseCase.saveBrand(brand);

        verify(persistencePort, times(1)).saveBrand(any(Brand.class));
    }

    @Test
    void testSaveBrandWithLongNameShouldFail() {
        String longName = "ThisCategoryNameIsWayTooLongAndShouldCauseAValidationError";
        String description = "Valid description";

        assertThrows(LengthFieldException.class, () -> new Brand(1L, longName, description));
    }

    @Test
    void testSaveBrandWithLongDescriptionShouldFail() {
        String name = "ValidName";
        String longDescription = "This description is way too long and should cause a validation error because it exceeds the maximum length allowed by the application rules.";

        assertThrows(LengthFieldException.class, () -> new Brand(1L, name, longDescription));
    }

    @Test
    void testSaveBrandWithEmptyNameShouldFail() {
        String emptyName = "";
        String description = "Valid description";

        assertThrows(EmptyFieldException.class, () -> new Brand(1L, emptyName, description));
    }

    @Test
    void testBrandWithEmptyDescriptionShouldFail() {
        String name = "ValidName";
        String emptyDescription = "";

        assertThrows(EmptyFieldException.class, () -> new Brand(1L, name, emptyDescription));
    }

    @Test
    void testSaveBrandThatAlreadyExistsShouldFail() {
        String name = "ExistingBrand";
        String description = "Valid description";
        Brand brand = new Brand(1L, name, description);

        when(persistencePort.findBrandByName(name)).thenReturn(Optional.of(brand));

        assertThrows(BrandAlreadyExistsException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    public void testGetAllBrands() {
        Pagination<Brand> pagination = new Pagination<>(
                Collections.singletonList(new Brand(1L,"brand1", "Description1")),
                0,
                10,
                1,
                1,
                false,
                false
        );
        when(persistencePort.getAllBrands(anyInt(), anyInt(), anyString())).thenReturn(pagination);

        Pagination<Brand> result = brandUseCase.getAllBrands(0, 10, "asc");

        assertNotNull(result);
        assertFalse(result.getList().isEmpty());
        assertEquals("brand1", result.getList().get(0).getName());
    }
}
