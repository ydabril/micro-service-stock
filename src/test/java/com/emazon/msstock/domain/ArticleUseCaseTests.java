package com.emazon.msstock.domain;

import com.emazon.msstock.domain.api.IBrandServicePort;
import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.api.use_case.ArticleUseCase;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

public class ArticleUseCaseTests {
    @Mock
    private IArticlePersistencePort articlePersistencePort;
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;
    @Mock
    private IBrandServicePort brandServicePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void SaveArticleTest(){
        Category category = new Category(1L, "name", "description");
        when(categoryPersistencePort.findCategoryById(1L)).thenReturn(Optional.of(category));

        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Article article = new Article(1L, "name", BigDecimal.ONE, Long.decode("1"), brand, categories );
        article.setCategories(categories);

        articleUseCase.saveArticle(article);

        verify(articlePersistencePort, times(1)).saveArticle(any(Article.class));
    }
}
