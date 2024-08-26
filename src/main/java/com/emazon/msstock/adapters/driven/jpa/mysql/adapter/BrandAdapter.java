package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.emazon.msstock.adapters.driven.jpa.mysql.exception.brand_exception.BrandAlreadyExistsException;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.Constants;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;
    @Override
    public void saveBrand(Brand brand) {
        if (brandRepository.findByName(brand.getName()).isPresent()) {
            throw new BrandAlreadyExistsException();
        }

        brandRepository.save(brandEntityMapper.toEntity(brand));
    }
}
