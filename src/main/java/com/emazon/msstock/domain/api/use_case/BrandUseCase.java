package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.IBrandServicePort;
import com.emazon.msstock.domain.exception.BrandAlreadyExistsException;
import com.emazon.msstock.domain.exception.CategoryAlreadyExistsException;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import java.util.List;

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
}
