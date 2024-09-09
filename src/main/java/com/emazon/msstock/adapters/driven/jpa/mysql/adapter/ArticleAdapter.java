package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.Constants;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ArticleAdapter implements IArticlePersistencePort {
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;
    @Override
    public void saveArticle(Article article) {
        articleRepository.save(articleEntityMapper.toEntity(article));
    }

    @Override
    public void addSupplies(Article article) {
        articleRepository.save(articleEntityMapper.toEntity(article));
    }

    @Override
    public Pagination<Article> getAllArticles(Integer page, Integer size, String sortBy, String sortDirection) {
        Sort sort;
        Page<ArticleEntity> articles = null;
        Pageable pagination;

        if(sortBy.equals(Constants.CATEGORIES)) {
            pagination = PageRequest.of(page, size);
            if (sortDirection.equals(Constants.SORT_DIRECTION_ASC)) {
                articles = articleRepository.findAllOrderByNumberOfCategoriesAsc(pagination);
            } else {
                articles = articleRepository.findAllOrderByNumberOfCategoriesDesc(pagination);
            }
        } else {
            Constants.SortBy sortByEnum = Constants.SortBy.valueOf(sortBy.toUpperCase());

            if (sortDirection.equalsIgnoreCase(Constants.SORT_DIRECTION_ASC)) {
                sort = Sort.by(sortByEnum.getFieldName()).ascending();
            } else {
                sort = Sort.by(sortByEnum.getFieldName()).descending();
            }

            pagination = PageRequest.of(page, size, sort);

            articles = articleRepository.findAll(pagination);

        }

        List<Article> articlesList = articles.getContent().stream()
                .map(articleEntityMapper::toModel)
                .distinct()
                .toList();


        return new Pagination<>(
                articlesList,
                articles.getNumber(),
                articles.getSize(),
                articles.getTotalElements(),
                articles.getTotalPages(),
                articles.hasNext(),
                articles.hasPrevious()
        );
    }

    @Override
    public Optional<Article> findArticleByName(String name) {
        return articleEntityMapper.toArticleOptional(articleRepository.findByName(name));
    }

    @Override
    public Optional<Article> findArticleById(Long id) {
        return articleEntityMapper.toArticleOptional(articleRepository.findById(id));
    }
}
