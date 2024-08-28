package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.msstock.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleResponseMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.api.IBrandServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestControllerAdapter {
    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;
    private final IArticleResponseMapper articleResponseMapper;

    @PostMapping
    public ResponseEntity<Void> addArticle(@RequestBody AddArticleRequest request) {
        articleServicePort.saveArticle(articleRequestMapper.addArticleRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {

        return ResponseEntity.ok(articleResponseMapper.
                toArticleResponseList(articleServicePort.getAllArticles()));
    }
}
