package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.Constants;
import com.emazon.msstock.domain.model.Brand;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void testGetAllBrands() {
        int page = 0;
        int size = 2;
        String sortDirection = Constants.SORT_DIRECTION_ASC;

        BrandEntity entity1 = new BrandEntity(1L, "brand 1", "Description 1");
        BrandEntity entity2 = new BrandEntity(2L, "brand 2", "Description 2");
        List<BrandEntity> entities = Arrays.asList(entity1, entity2);

        Page<BrandEntity> brandPage = new PageImpl<>(entities, PageRequest.of(page, size, Sort.by("name").ascending()), entities.size());

        when(brandRepository.findAll(any(PageRequest.class))).thenReturn(brandPage);

        Brand model1 = new Brand(1L, "brand 1", "Description 1");
        Brand model2 = new Brand(2L, "brand 2", "Description 2");
        List<Brand> models = Arrays.asList(model1, model2);
        when(brandEntityMapper.toModelList(entities)).thenReturn(models);

        Pagination<Brand> result = brandAdapter.getAllBrands(page, size, sortDirection);

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
