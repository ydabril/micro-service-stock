package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleResponseMapper;
import com.emazon.msstock.adapters.driving.http.utils.ValidateParametersConstants;
import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/article")
@Validated
@RequiredArgsConstructor
public class ArticleRestControllerAdapter {
    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;
    private final IArticleResponseMapper articleResponseMapper;

    @PostMapping
    public ResponseEntity<Void> addArticle(@RequestBody AddArticleRequest request) {
        List<Category> categoryRequest = articleRequestMapper.articleToCategoryList(request);
        Article article = articleRequestMapper.addArticleRequest(request);
        article.setCategories(categoryRequest);
        articleServicePort.saveArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<Pagination<ArticleResponse>> getAllArticles(
            @Min(value = 0, message = ValidateParametersConstants.PAGE_NUMBER_NEGATIVE)
            @RequestParam(defaultValue = "0") int page,

            @Min(value = 1, message = ValidateParametersConstants.PAGE_SIZE_INVALID)
            @RequestParam(defaultValue = "10") int size,

            @Pattern(regexp = ValidateParametersConstants.SORT_DIRECTION_PATTERN, message = ValidateParametersConstants.INVALID_SORT_DIRECTION)
            @RequestParam(defaultValue = "ASC") String sortDirection,

            @Pattern(regexp = ValidateParametersConstants.SORT_BY_PATTERN, message = ValidateParametersConstants.INVALID_SORT_PROPERTY)
            @RequestParam(defaultValue = "ARTICLE_NAME") String sortBy) {

        Pagination<ArticleResponse> articles = articleResponseMapper
                .toPaginationResponse(articleServicePort.getAllArticles(page, size, sortBy, sortDirection));

        return ResponseEntity.ok(articles);
    }
}
