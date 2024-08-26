package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.exception.article_exception.ArticleAlreadyExistsException;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleAdapter implements IArticlePersistencePort {
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;
    @Override
    public void saveArticle(Article article) {
        if (articleRepository.findByName(article.getName()).isPresent()) {
            throw new ArticleAlreadyExistsException();
        }

        articleRepository.save(articleEntityMapper.toEntity(article));
    }
}
