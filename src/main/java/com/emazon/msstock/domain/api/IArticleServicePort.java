package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Article;

import java.util.List;

public interface IArticleServicePort {
    void saveArticle(Article article);
    List<Article> getAllArticles();
}
