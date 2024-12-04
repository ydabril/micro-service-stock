package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.exception.*;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.model.Supply;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import com.emazon.msstock.domain.util.DomainConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ArticleUseCase implements IArticleServicePort {
    private IArticlePersistencePort articlePersistencePort;
    private ICategoryPersistencePort categoryPersistencePort;
    private IBrandPersistencePort brandPersistencePort;

    private final String imageDirectory = "uploads/img/";
    private final String urlBase = "http://localhost:8081/img/";

    public  ArticleUseCase(IArticlePersistencePort articlePersistencePort,ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }
    @Override
    public void saveArticle(Article article, MultipartFile image) {
        if (articlePersistencePort.findArticleByName(article.getName()).isPresent()) {
            throw new ArticleAlreadyExistsException();
        }

        if (article.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeNotAllowedException(DomainConstants.FieldArticle.PRICE.toString());
        }
        if (article.getQuantity() < 0) {
            throw new NegativeNotAllowedException(DomainConstants.FieldArticle.QUANTITY.toString());
        }

        if(article.getCategories() == null || article.getCategories().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FieldArticle.CATEGORIES.toString());
        }

        List<Long> categoryIds = article.getCategories().stream()
                .map(Category::getId)
                .toList();

        Map<Long, Long> frequencyMap = categoryIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        List<Long> duplicatedIds = frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        findExistingCategories(categoryIds);

        if (brandPersistencePort.findBrandById(article.getBrand().getId()).isEmpty()){
            throw new BrandNoDataFoundException();
        }

        if (categoryIds.size() < DomainConstants.MINIMUM_COUNT_CATEGORY || categoryIds.size() > DomainConstants.MAXIMUM_COUNT_CATEGORY) {
            throw new InvalidCategoryCountException(DomainConstants.MAXIMUM_COUNT_CATEGORY);
        }

        if (!duplicatedIds.isEmpty()) {
            throw new DuplicateCategoryExceptiom();
        }

        String imagePath = saveImage(image);
        article.setImagePath(urlBase + imagePath);

        articlePersistencePort.saveArticle(article);
    }

    public String saveImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("No se proporcionó una imagen");
        }

        try {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(imageDirectory + fileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    @Override
    public Pagination<Article> getAllArticles(Integer page, Integer size, String sortBy, String sortDirection) {
        return articlePersistencePort.getAllArticles(page, size, sortBy, sortDirection);
    }

    @Override
    public Pagination<Article> getFilteredArticlesById(Integer page, Integer size, String sortDirection, List<Long> articleIds, String categoryName, String brandName) {
        return articlePersistencePort.getArticlesCartById(page, size, sortDirection, articleIds, categoryName, brandName);
    }

    @Override
    public void addSupplies(Supply supply) {
        if (supply.getQuantity() <= DomainConstants.MIN_QUANTITY) {
            throw new NegativeNotAllowedException(DomainConstants.FieldArticle.QUANTITY.toString());
        }

        Article article = articlePersistencePort.findArticleById(supply.getArticleId())
                .orElseThrow(ArticleNoDataFoundException::new);

        long newQuantity = article.getQuantity() + supply.getQuantity();

        article.setQuantity(newQuantity);

        articlePersistencePort.addSupplies(article);
    }

    @Override
    public void subtractStock(Supply articleSubtract) {
            if (articleSubtract.getQuantity() <= DomainConstants.MIN_QUANTITY) {
                throw new NegativeNotAllowedException(DomainConstants.FieldArticle.QUANTITY.toString());
            }

            Article article = articlePersistencePort.findArticleById(articleSubtract.getArticleId())
                    .orElseThrow(ArticleNoDataFoundException::new);

            if (articleSubtract.getQuantity() > article.getQuantity())  {
                throw new OutOfStockException();
            }

            long newQuantity = article.getQuantity() - articleSubtract.getQuantity();

            article.setQuantity(newQuantity);

            articlePersistencePort.saveArticle(article);
    }

    @Override
    public Optional<Article> getArticle(Long articleId) {
        return  articlePersistencePort.findArticleById(articleId);
    }

    public void findExistingCategories(List<Long> categoriesIds) {
        for (Long id : categoriesIds) {
            if(categoryPersistencePort.findCategoryById(id).isEmpty()) {
                throw new CategoryNoDataFoundException();
            }
        }
    }
}
