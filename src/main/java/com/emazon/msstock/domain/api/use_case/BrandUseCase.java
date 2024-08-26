package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.IBrandServicePort;
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
        brandPersistencePort.saveBrand(brand);
    }
}
