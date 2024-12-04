package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.AddSuppliesRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.ArticleFilterRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.ArticleSubtract;
import com.emazon.msstock.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleResponseMapper;
import com.emazon.msstock.adapters.driving.http.utils.ValidateParametersConstants;
import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.model.Supply;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/article")
@Validated
@RequiredArgsConstructor
public class ArticleRestControllerAdapter {
    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;
    private final IArticleResponseMapper articleResponseMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Save an article", description = "Creates a new article in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addArticle(
            @RequestPart("articleData") AddArticleRequest request,
            @RequestPart("image") MultipartFile image) {

        // Convierte el request a tu modelo de dominio
        List<Category> categoryRequest = articleRequestMapper.articleToCategoryList(request);
        Article article = articleRequestMapper.addArticleRequest(request);
        article.setCategories(categoryRequest);

        // Guarda el artículo
        articleServicePort.saveArticle(article, image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Get articles sorted by criteria", description = "Retrieves a list of articles sorted by name, brand name, or category name and paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articles successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/all")
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

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/getPaginatedArticlesById")
    public ResponseEntity<Pagination<ArticleResponse>> getArticlesCartById(@RequestBody ArticleFilterRequest filterRequest) {
        List<Long> articleIds = filterRequest.getArticleIds();
        String sortDirection = filterRequest.getSortDirection();
        Integer page = filterRequest.getPage();
        Integer size = filterRequest.getSize();
        String categoryName = filterRequest.getCategoryName();
        String brandName = filterRequest.getBrandName();

        Pagination<ArticleResponse> articles = articleResponseMapper
                .toPaginationResponse(articleServicePort.getFilteredArticlesById(page, size, sortDirection, articleIds, categoryName, brandName));

        return ResponseEntity.ok(articles);
    }

    @PreAuthorize("hasRole('AUX_BODEGA') || hasRole('ADMIN')")
    @Operation(summary = "Update stock", description = "increases the amount of existing supplies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "stock updated"),
            @ApiResponse(responseCode = "401", description = "unauthorized access"),
            @ApiResponse(responseCode = "403", description = "access prohibited"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<Void> updateStockArticles(@RequestBody AddSuppliesRequest request) {
        articleServicePort.addSupplies(articleRequestMapper.addSupplyRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/subtract-stock")
    public ResponseEntity<Void> subtractStock(@RequestBody ArticleSubtract request) {
        Supply articleSubtract = new Supply(request.getArticleId(), request.getQuantity());
        articleServicePort.subtractStock(articleSubtract);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{articleId}")
    Optional<Article> getArticle(@PathVariable Long articleId) {
        return articleServicePort.getArticle(articleId);
    }
}
