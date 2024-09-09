package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.model.Supply;
import org.springframework.http.ResponseEntity;

public interface IArticleServicePort {
    void saveArticle(Article article);
    void addSupplies(Supply supply);
    Pagination<Article> getAllArticles(Integer page, Integer size, String sortBy, String sortDirection);
}
