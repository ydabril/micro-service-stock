package com.emazon.msstock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.domain.model.Article;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface IArticleEntityMapper {

    Article toModel(ArticleEntity articleEntity);

    ArticleEntity toEntity(Article article);
    List<Article> toModelList(List<ArticleEntity> articleEntities);

    default Optional<Article> toArtuckeOptional(Optional<ArticleEntity> articleEntityOptional) {
        return articleEntityOptional.map(this::toModel);
    }
}
