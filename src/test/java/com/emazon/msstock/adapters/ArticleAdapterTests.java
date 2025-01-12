package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driven.jpa.mysql.adapter.ArticleAdapter;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArticleAdapterTests {
    @Mock
    private IArticleRepository iArticleRepository;

    @Mock
    private IArticleEntityMapper articleEntityMapper;

    @InjectMocks
    private ArticleAdapter articleAdapter;

    private Article article;
    private ArticleEntity articleEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        article = new Article(1L, "name", BigDecimal.ONE, Long.decode("1"), new Brand(1L, "name", "description"), new ArrayList<>());
        article.setCategories(new ArrayList<>());

        articleEntity = new ArticleEntity();
    }

    @Test
    void saveArticleShouldSaveArticle() {
        when(articleEntityMapper.toEntity(article)).thenReturn(articleEntity);

        articleAdapter.saveArticle(article);

        verify(iArticleRepository).save(articleEntity);
    }

    @Test
    void testFindArticleByName() {
        String name = "name";
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setName(name);

        when(iArticleRepository.findByName(name)).thenReturn(Optional.of(articleEntity));
        when(articleEntityMapper.toArticleOptional(Optional.of(articleEntity)))
                .thenReturn(Optional.of(new Article(1L, "name", BigDecimal.ONE, Long.decode("1"), new Brand(1L, "name", "description"), new ArrayList<>())));

        Optional<Article> result = articleAdapter.findArticleByName(name);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());

        verify(iArticleRepository).findByName(name);
    }

    @Test
    void testAdapterRetrievePaginatedArticlesReturnsCorrectResults() {
        // Arrange
        int page = 0;
        int size = 2;
        String sortBy = "ARTICLE_NAME";
        String sortDirection = "ASC";

        ArticleEntity entity1 = new ArticleEntity();
        ArticleEntity entity2 = new ArticleEntity();
        List<ArticleEntity> entities = Arrays.asList(entity1, entity2);

        Page<ArticleEntity> articlePage = new PageImpl<>(entities, PageRequest.of(page, size, Sort.by("name").ascending()), entities.size());

        when(iArticleRepository.findAll(any(PageRequest.class))).thenReturn(articlePage);

        Article model1 = new Article(1L, "name", BigDecimal.ONE, Long.decode("1"), new Brand(1L, "name", "description"), new ArrayList<>());
        Article model2 = new Article(2L, "name2", BigDecimal.ONE, Long.decode("1"), new Brand(2L, "name", "description"), new ArrayList<>());
        List<Article> models = Arrays.asList(model1, model2);

        when(articleEntityMapper.toModel(any(ArticleEntity.class))).thenAnswer(invocation -> {
            ArticleEntity entity = invocation.getArgument(0);
            if (entity == entity1) return model1;
            else return model2;
        });

        // Act
        Pagination<Article> result = articleAdapter.getAllArticles(page, size, sortBy, sortDirection);

        // Assert
        assertNotNull(result);
        assertEquals(page, result.getCurrentPage());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertFalse(result.getList().isEmpty());
        assertEquals(models, result.getList());
        assertFalse(result.isHasNextPage());
        assertFalse(result.isHasPreviousPage());
    }
}
