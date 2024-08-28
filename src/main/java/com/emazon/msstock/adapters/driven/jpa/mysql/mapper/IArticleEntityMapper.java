package com.emazon.msstock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleEntityMapper {

    Article toModel(ArticleEntity articleEntity);

    ArticleEntity toEntity(Article article);
    List<Article> toModelList(List<ArticleEntity> articleEntities);
}
