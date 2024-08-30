package com.emazon.msstock.adapters.driving.http.mapper;

import com.emazon.msstock.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
    CategoryResponse toCategoryResponseResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
    Pagination<CategoryResponse> toPaginationResponse(Pagination<Category> pagination);
}
