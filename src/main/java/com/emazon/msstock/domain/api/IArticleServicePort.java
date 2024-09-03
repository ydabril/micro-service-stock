package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Pagination;

public interface IArticleServicePort {
    void saveArticle(Article article);
    Pagination<Article> getAllArticles(Integer page, Integer size, String sortBy, String sortDirection);
}
