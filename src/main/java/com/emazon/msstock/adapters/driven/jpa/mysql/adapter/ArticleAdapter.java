package com.emazon.msstock.adapters.driven.jpa.mysql.adapter;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import lombok.RequiredArgsConstructor;

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
    public List<Article> getAllArticles() {
        List<ArticleEntity> articleEntities = articleRepository.findAll();
        return articleEntityMapper.toModelList(articleEntities);
    }

    @Override
    public Optional<Article> findArticleByName(String name) {
        return articleEntityMapper.toArtuckeOptional(articleRepository.findByName(name));
    }
}
