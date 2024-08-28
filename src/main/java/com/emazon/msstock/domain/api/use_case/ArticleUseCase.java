package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.exception.DuplicateCategoryExceptiom;
import com.emazon.msstock.domain.exception.InvalidCategoryCountException;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import com.emazon.msstock.domain.util.DomainConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleUseCase implements IArticleServicePort {
    private IArticlePersistencePort articlePersistencePort;
    private ICategoryPersistencePort categoryPersistencePort;
    public  ArticleUseCase(IArticlePersistencePort articlePersistencePort,ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void saveArticle(Article article) {
        List<Long> categoryIds = article.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        Map<Long, Long> frequencyMap = categoryIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        List<Long> duplicatedIds = frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (categoryIds.size() < DomainConstants.MINIMUM_COUNT_CATEGORY || categoryIds.size() > DomainConstants.MAXIMUM_COUNT_CATEGORY) {
            throw new InvalidCategoryCountException(DomainConstants.MAXIMUM_COUNT_CATEGORY);
        }

        if (!duplicatedIds.isEmpty()) {
            throw new DuplicateCategoryExceptiom();
        }

        articlePersistencePort.saveArticle(article);
    }

    @Override
    public  List<Article> getAllArticles() {
        return articlePersistencePort.getAllArticles();
    }
}
