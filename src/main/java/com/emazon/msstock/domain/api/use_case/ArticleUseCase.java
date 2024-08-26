package com.emazon.msstock.domain.api.use_case;

import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.spi.IArticlePersistencePort;
import com.emazon.msstock.domain.spi.IBrandPersistencePort;

public class ArticleUseCase implements IArticleServicePort {
    private IArticlePersistencePort articlePersistencePort;

    public  ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }
    @Override
    public void saveArticle(Article article) {
        articlePersistencePort.saveArticle(article);
    }
}
