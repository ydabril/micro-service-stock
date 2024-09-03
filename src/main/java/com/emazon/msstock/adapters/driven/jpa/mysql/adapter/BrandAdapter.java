package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.Constants;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public Pagination<Brand> getAllBrands(Integer page, Integer size, String sortDirection) {
        Sort sort = sortDirection.equals(Constants.SORT_DIRECTION_ASC) ? Sort.by(Constants.NAME).ascending() : Sort.by(Constants.NAME).descending();
        Pageable pagination = PageRequest.of(page, size, sort);
        Page<BrandEntity> brands = brandRepository.findAll(pagination);

        return new Pagination<>(
                brandEntityMapper.toModelList(brands.getContent()),
                brands.getNumber(),
                brands.getSize(),
                brands.getTotalElements(),
                brands.getTotalPages(),
                brands.hasNext(),
                brands.hasPrevious()
        );
    }

    @Override
    public Optional<Brand> findBrandByName(String name) {
        return brandEntityMapper.toBrandOptional(brandRepository.findByName(name));
    }

    @Override
    public Optional<Brand> findBrandById(Long id) {
        return brandEntityMapper.toBrandOptional(brandRepository.findById(id));
    }
}
