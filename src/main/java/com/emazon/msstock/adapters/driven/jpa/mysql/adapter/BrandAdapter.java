package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;
    @Override
    public void saveBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public Optional<Brand> findBrandByName(String name) {
        return brandEntityMapper.toBrandOptional(brandRepository.findByName(name));
    }
}
