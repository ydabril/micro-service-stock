package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Pagination;

import java.util.Optional;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);

    Pagination<Brand> getAllBrands(Integer page, Integer size, String sortDirection);

    public Optional<Brand> findBrandByName(String name);
}