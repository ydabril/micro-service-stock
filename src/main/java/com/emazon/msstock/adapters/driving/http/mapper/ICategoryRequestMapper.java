package com.emazon.msstock.adapters.driving.http.mapper;

import com.emazon.msstock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.msstock.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {
    Category addCategoryRequest(AddCategoryRequest addCategoryRequest);
}
