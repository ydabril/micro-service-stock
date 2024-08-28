package com.emazon.msstock.adapters.driving.http.mapper;

import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IArticleRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(target = "brand.name", constant = "name")
    @Mapping(target = "brand.description", constant = "description")
    @Mapping(source = "categoryIds", target = "categories")
    Article addArticleRequest(AddArticleRequest addArticleRequest);

    default List<Category> mapCategories(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> new Category(id, "name", "description"))
                .collect(Collectors.toList());
    }
}
