package com.emazon.msstock.adapters.driving.http.mapper;

import com.emazon.msstock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msstock.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    Brand addBrandRequest(AddBrandRequest addBrandRequest);
}
