package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driven.jpa.mysql.adapter.ArticleAdapter;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        article = new Article(1L, "name", BigDecimal.ONE, Long.decode("1"), new Brand(1L, "name", "description"), new ArrayList<>() );
        article.setCategories(new ArrayList<>());

        articleEntity = new ArticleEntity();
    }

    @Test
    void saveArticleShouldSaveArticle() {
        when(articleEntityMapper.toEntity(article)).thenReturn(articleEntity);

        articleAdapter.saveArticle(article);

        verify(iArticleRepository).save(articleEntity);
    }


}
