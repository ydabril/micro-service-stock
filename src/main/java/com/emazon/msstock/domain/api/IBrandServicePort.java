package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Pagination;

public interface IBrandServicePort {
    void saveBrand(Brand brand);

    Pagination<Brand> getAllBrands(Integer page, Integer size, String sortDirection);
}
