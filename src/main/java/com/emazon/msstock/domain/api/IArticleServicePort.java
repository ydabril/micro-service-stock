package com.emazon.msstock.domain.api;

import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.model.Supply;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IArticleServicePort {
    void saveArticle(Article article, MultipartFile image);
    void addSupplies(Supply supply);

    void subtractStock(Supply articleSubtract);
    Pagination<Article> getAllArticles(Integer page, Integer size, String sortBy, String sortDirection);

    Pagination<Article> getFilteredArticlesById(Integer page, Integer size, String sortDirection, List<Long> articleIds, String categoryName, String brandName);
    Optional<Article> getArticle(Long articleId);
}
