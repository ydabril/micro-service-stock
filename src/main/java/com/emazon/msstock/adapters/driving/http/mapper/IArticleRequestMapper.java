package com.emazon.msstock.adapters.driving.http.mapper;

import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.AddSuppliesRequest;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(target = "brand.name", constant = "name")
    @Mapping(target = "brand.description", constant = "description")
    @Mapping(target = "categories", ignore = true)
    Article addArticleRequest(AddArticleRequest addArticleRequest);
    Supply addSupplyRequest(AddSuppliesRequest addSuppliesRequest);

    default List<Category> articleToCategoryList(AddArticleRequest articleRequest) {
        return articleRequest.getCategoryIds().stream().map(id -> new Category(id, null, null)).toList();
    }
}
