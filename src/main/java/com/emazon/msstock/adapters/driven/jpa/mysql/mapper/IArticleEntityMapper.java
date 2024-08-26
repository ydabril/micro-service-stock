package com.emazon.msstock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IArticleEntityMapper {
    @Mapping(source = "brand.id", target = "brand.id")
    @Mapping(source = "brand.name", target = "brand.name")
    @Mapping(source = "brand.description", target = "brand.description")
    Article toModel(ArticleEntity articleEntity);
    @Mapping(source = "brand.id", target = "brand.id")
    @Mapping(source = "brand.name", target = "brand.name")
    @Mapping(source = "brand.description", target = "brand.description")
    ArticleEntity toEntity(Article article);
}
