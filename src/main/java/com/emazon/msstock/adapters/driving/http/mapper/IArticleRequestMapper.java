package com.emazon.msstock.adapters.driving.http.mapper;

import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IArticleRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(target = "brand.name", constant = "name")
    @Mapping(target = "brand.description", constant = "description")
    Article addArticleRequest(AddArticleRequest addArticleRequest);
}
