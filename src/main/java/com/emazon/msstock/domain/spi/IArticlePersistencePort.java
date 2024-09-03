package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Article;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistencePort {
    void saveArticle(Article article);
    List<Article> getAllArticles();
    Optional<Article> findArticleByName(String name);
}
