package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Brand;

import java.util.List;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
}
