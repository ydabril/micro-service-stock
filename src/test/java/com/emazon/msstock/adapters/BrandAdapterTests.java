package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msstock.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BrandAdapterTests {
    @Mock
    private IBrandRepository brandRepository;
    @Mock
    private IBrandEntityMapper brandEntityMapper;
    @InjectMocks
    private BrandAdapter brandAdapter;
    private Brand brand;
    private BrandEntity brandEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brand = new Brand(1L, "Valid Name", "Valid Description");
        brandEntity = new BrandEntity();
        brandEntity.setId(1L);
        brandEntity.setName("Valid Name");
        brandEntity.setDescription("Valid Description");
    }

    @Test
    void testSaveBrand() {
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);

        brandAdapter.saveBrand(brand);

        verify(brandRepository).save(brandEntity);
    }

    @Test
    void testFindBrandByName() {
        String name = "Valid Name";
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);

        when(brandRepository.findByName(name)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrandOptional(Optional.of(brandEntity)))
                .thenReturn(Optional.of(new Brand(1L, name, "Valid Description")));

        Optional<Brand> result = brandAdapter.findBrandByName(name);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());

        verify(brandRepository).findByName(name);
    }
}
