package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.IBrandServicePort;
import com.emazon.msstock.domain.exception.BrandAlreadyExistsException;
import com.emazon.msstock.domain.exception.NoDataFoundException;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;

public class BrandUseCase implements IBrandServicePort {
    private IBrandPersistencePort brandPersistencePort;

    public  BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }
    @Override
    public void saveBrand(Brand brand) {
        if(brandPersistencePort.findBrandByName(brand.getName()).isPresent()){
            throw new BrandAlreadyExistsException();
        }
        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public Pagination<Brand> getAllBrands(Integer page, Integer size, String sortDirection) {
        Pagination<Brand> brands = brandPersistencePort.getAllBrands(page, size, sortDirection);
        if(brands.getList().isEmpty()){
            throw  new NoDataFoundException();
        }
        return brands;
    }

}
