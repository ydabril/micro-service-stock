package com.emazon.msstock.domain;

import com.emazon.msstock.domain.api.IBrandServicePort;
import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.api.use_case.ArticleUseCase;
import com.emazon.msstock.domain.exception.*;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testSaveArticleWithNegativePriceShouldFail() {
        Article article = new Article(1L, "name", BigDecimal.valueOf(-1), Long.decode("1"), new Brand(1L, "name", "description"), new ArrayList<>());

        assertThrows(NegativeNotAllowedException.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    void testSaveArticleWithNegativeQuantityShouldFail() {
        Article article = new Article(1L, "name", BigDecimal.valueOf(10), Long.valueOf(-1), new Brand(1L, "name", "description"), new ArrayList<>());

        assertThrows(NegativeNotAllowedException.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    void testSaveArticleWithEmptyCategoriesShouldFail() {
        List<Category> categories = new ArrayList<>();
        anyList().add(null);
        Article article = new Article(1L, "name", BigDecimal.valueOf(10), Long.valueOf(1), new Brand(1L, "name", "description"), categories);

        assertThrows(EmptyFieldException.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    void testSaveArticleWithDuplicateCategoriesShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));
        categories.add(new Category(1L, "categoria2", "descripcion"));
        when(categoryPersistencePort.findCategoryById(1L)).thenReturn(Optional.of(new Category(1L, "categoria1", "descripcion")));

        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        Article article = new Article(1L, "name", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories);

        assertThrows(DuplicateCategoryExceptiom.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    void testSaveArticleWithInvalidCategoryCountShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));
        categories.add(new Category(2L, "categoria2", "descripcion"));
        categories.add(new Category(3L, "categoria3", "descripcion"));
        categories.add(new Category(4L, "categoria4", "descripcion"));
        when(categoryPersistencePort.findCategoryById(1L)).thenReturn(Optional.of(new Category(1L, "categoria1", "descripcion")));
        when(categoryPersistencePort.findCategoryById(2L)).thenReturn(Optional.of(new Category(2L, "categoria2", "descripcion")));
        when(categoryPersistencePort.findCategoryById(3L)).thenReturn(Optional.of(new Category(3L, "categoria3", "descripcion")));
        when(categoryPersistencePort.findCategoryById(4L)).thenReturn(Optional.of(new Category(4L, "categoria4", "descripcion")));

        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        Article article = new Article(1L, "name", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories);

        assertThrows(InvalidCategoryCountException.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    void testSaveArticleWithNoDataFoundCategoriesShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));

        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        Article article = new Article(1L, "name", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories);

        assertThrows(CategoryNoDataFoundException.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    void testSaveArticleWithNoDataFoundBrandsShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));
        categories.add(new Category(1L, "categoria2", "descripcion"));
        when(categoryPersistencePort.findCategoryById(1L)).thenReturn(Optional.of(new Category(1L, "categoria1", "descripcion")));

        Brand brand = new Brand(1L, "name", "description");

        Article article = new Article(1L, "name", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories);

        assertThrows(BrandNoDataFoundException.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    void testSaveArticleAlreadyExistsShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));
        when(categoryPersistencePort.findCategoryById(1L)).thenReturn(Optional.of(new Category(1L, "categoria1", "descripcion")));

        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        Article article = new Article(1L, "name", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories);

        when(articlePersistencePort.findArticleByName(article.getName()))
                .thenReturn(Optional.of(article));

        assertThrows(ArticleAlreadyExistsException.class, () -> {
            articleUseCase.saveArticle(article);
        });
    }

    @Test
    public void testGetAllArticles() {
        Brand brand = new Brand(1L, "name", "description");
        List<Category> categories = new ArrayList<>();
        Pagination<Article> pagination = new Pagination<>(
                Collections.singletonList(new Article(1L, "articleName", BigDecimal.ONE, Long.decode("1"), brand, categories )),
                0,
                10,
                1,
                1,
                false,
                false
        );

        when(articlePersistencePort.getAllArticles(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pagination);

        Pagination<Article> result = articleUseCase.getAllArticles(0, 10, "CATEGORIES", "ASC");

        assertNotNull(result);
        assertFalse(result.getList().isEmpty());
        assertEquals("articleName", result.getList().get(0).getName());
    }
}
