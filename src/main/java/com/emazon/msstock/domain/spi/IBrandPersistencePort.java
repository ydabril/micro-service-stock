package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    public Optional<Brand> findBrandByName(String name);
}
