package com.emazon.msstock.adapters.driven.jpa.mysql.repository;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long>  {
    Optional<ArticleEntity> findByName(String name);
    @Query("SELECT a FROM ArticleEntity a JOIN FETCH a.brand b JOIN FETCH a.categories c")
    List<ArticleEntity> findAll();
}
