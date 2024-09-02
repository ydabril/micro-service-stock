package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.exception.*;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import com.emazon.msstock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleUseCase implements IArticleServicePort {
    private IArticlePersistencePort articlePersistencePort;
    private ICategoryPersistencePort categoryPersistencePort;
    private IBrandPersistencePort brandPersistencePort;
    public  ArticleUseCase(IArticlePersistencePort articlePersistencePort,ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }
    @Override
    public void saveArticle(Article article) {
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

        articlePersistencePort.saveArticle(article);
    }

    @Override
    public Pagination<Article> getAllArticles(Integer page, Integer size, String sortBy, String sortDirection) {
        return articlePersistencePort.getAllArticles(page, size, sortBy, sortDirection);
    }

    public void findExistingCategories(List<Long> categoriesIds) {
        for (Long id : categoriesIds) {
            if(categoryPersistencePort.findCategoryById(id).isEmpty()) {
                throw new CategoryNoDataFoundException();
            }
        }
    }
}
