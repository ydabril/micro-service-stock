package com.emazon.msstock.domain.spi;

import com.emazon.msstock.domain.model.Article;

public interface IArticlePersistencePort {
    void saveArticle(Article article);
}
