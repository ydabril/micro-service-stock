package com.emazon.msstock.adapters.driving.http.mapper;

import com.emazon.msstock.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.msstock.domain.model.Article;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleResponseMapper {
    ArticleResponse toArticleResponse(Article article);
    List<ArticleResponse> toArticleResponseList(List<Article> articles);
}
