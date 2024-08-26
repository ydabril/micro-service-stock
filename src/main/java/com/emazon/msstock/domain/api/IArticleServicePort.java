package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Article;

public interface IArticleServicePort {
    void saveArticle(Article article);
}
