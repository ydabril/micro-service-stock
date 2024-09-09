package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Pagination;

import java.util.Optional;

public interface IArticlePersistencePort {
    void saveArticle(Article article);

    void addSupplies(Article article);
    Pagination<Article> getAllArticles(Integer page, Integer size, String sortBy, String sortDirection);
    Optional<Article> findArticleByName(String name);

    Optional<Article> findArticleById(Long id);
}
