package com.emazon.msstock.domain;

import com.emazon.msstock.domain.api.IBrandServicePort;
import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.api.use_case.ArticleUseCase;
import com.emazon.msstock.domain.exception.*;
import com.emazon.msstock.domain.model.*;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

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

        Article article = new Article(1L, "name", "description", BigDecimal.ONE, Long.decode("1"), brand, categories, "image-path" );
        article.setCategories(categories);

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",                         // Nombre del archivo
                "test.jpg",                      // Nombre original del archivo
                "image/jpeg",                    // Tipo de contenido
                "fake-image-content".getBytes()  // Contenido en bytes
        );

        articleUseCase.saveArticle(article, mockFile);

        verify(articlePersistencePort, times(1)).saveArticle(any(Article.class));
    }

    @Test
    void testSaveArticleWithNegativePriceShouldFail() {
        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(-1), Long.decode("1"), new Brand(1L, "name", "description"), new ArrayList<>(), "image-path" );

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        assertThrows(NegativeNotAllowedException.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
        });
    }

    @Test
    void testSaveArticleWithNegativeQuantityShouldFail() {
        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(10), Long.valueOf(-1), new Brand(1L, "name", "description"), new ArrayList<>(), "image-path");

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        assertThrows(NegativeNotAllowedException.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
        });
    }

    @Test
    void testSaveArticleWithEmptyCategoriesShouldFail() {
        List<Category> categories = new ArrayList<>();
        anyList().add(null);
        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(10), Long.valueOf(1), new Brand(1L, "name", "description"), categories, "image-path");

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        assertThrows(EmptyFieldException.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
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

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories, "image-path");



        assertThrows(DuplicateCategoryExceptiom.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
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

        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories, "image-path");

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );


        assertThrows(InvalidCategoryCountException.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
        });
    }

    @Test
    void testSaveArticleWithNoDataFoundCategoriesShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));

        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories, "image-path");

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );


        assertThrows(CategoryNoDataFoundException.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
        });
    }

    @Test
    void testSaveArticleWithNoDataFoundBrandsShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));
        categories.add(new Category(1L, "categoria2", "descripcion"));
        when(categoryPersistencePort.findCategoryById(1L)).thenReturn(Optional.of(new Category(1L, "categoria1", "descripcion")));

        Brand brand = new Brand(1L, "name", "description");

        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories, "image-path");

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        assertThrows(BrandNoDataFoundException.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
        });
    }

    @Test
    void testSaveArticleAlreadyExistsShouldFail() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "categoria1", "descripcion"));
        when(categoryPersistencePort.findCategoryById(1L)).thenReturn(Optional.of(new Category(1L, "categoria1", "descripcion")));

        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        Article article = new Article(1L, "name", "description", BigDecimal.valueOf(10), Long.valueOf(1), brand, categories, "image-path");

        when(articlePersistencePort.findArticleByName(article.getName()))
                .thenReturn(Optional.of(article));

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        assertThrows(ArticleAlreadyExistsException.class, () -> {
            articleUseCase.saveArticle(article, mockFile);
        });
    }

    @Test
    public void testGetAllArticles() {
        Brand brand = new Brand(1L, "name", "description");
        List<Category> categories = new ArrayList<>();
        Pagination<Article> pagination = new Pagination<>(
                Collections.singletonList(new Article(1L, "articleName", "description", BigDecimal.ONE, Long.decode("1"), brand, categories, "image-path" )),
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

    @Test
    void AddSuppliesSuccessTest(){
        Category category = new Category(1L, "name", "description");
        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Article article = new Article(1L, "name", "description", BigDecimal.ONE,  Long.decode("1"), brand, categories, "image-path" );
        article.setCategories(categories);

        when(articlePersistencePort.findArticleById(1L)).thenReturn(Optional.of(article));

        Supply supply = new Supply(1L, 10L);

        articleUseCase.addSupplies(supply);

        verify(articlePersistencePort, times(1)).addSupplies(any(Article.class));
    }

    @Test
    void AddSuppliesWithArticleNoDataFoundExceptionTest(){
        Category category = new Category(1L, "name", "description");
        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Article article = new Article(1L, "name", "description", BigDecimal.ONE, Long.decode("1"), brand, categories, "image-path" );
        article.setCategories(categories);

        Supply supply = new Supply(1L, 10L);

        assertThrows(ArticleNoDataFoundException.class, () -> {
            articleUseCase.addSupplies(supply);
        });
    }

    @Test
    void AddSuppliesWithNegativeQuantityExceptionTest(){
        Category category = new Category(1L, "name", "description");
        Brand brand = new Brand(1L, "name", "description");
        when(brandPersistencePort.findBrandById(1L)).thenReturn(Optional.of(brand));

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Article article = new Article(1L, "name", "description", BigDecimal.ONE, Long.decode("1"), brand, categories, "image-path" );
        article.setCategories(categories);

        when(articlePersistencePort.findArticleById(1L)).thenReturn(Optional.of(article));

        Supply supply = new Supply(1L, -10L);

        assertThrows(NegativeNotAllowedException.class, () -> {
            articleUseCase.addSupplies(supply);
        });
    }
}
