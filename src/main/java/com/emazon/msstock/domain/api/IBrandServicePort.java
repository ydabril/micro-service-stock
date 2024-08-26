package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Brand;
import java.util.List;

public interface IBrandServicePort {
    void saveBrand(Brand brand);
    List<Brand> getAllBrands(Integer page, Integer size, String sortDirection);
}
