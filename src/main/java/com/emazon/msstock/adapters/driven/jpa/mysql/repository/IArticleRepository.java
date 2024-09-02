package com.emazon.msstock.adapters.driven.jpa.mysql.repository;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.ConstantsQuerys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long>  {
    Optional<ArticleEntity> findByName(String name);

    @Query(ConstantsQuerys.FIND_ALL_ORDER_BY_FIRST_CATEGORY_NAME_ASC)
    Page<ArticleEntity> findAllOrderByNumberOfCategoriesAsc(Pageable pageable);
    @Query(ConstantsQuerys.FIND_ALL_ORDER_BY_FIRST_CATEGORY_NAME_DESC)
    Page<ArticleEntity> findAllOrderByNumberOfCategoriesDesc(Pageable pageable);



}
