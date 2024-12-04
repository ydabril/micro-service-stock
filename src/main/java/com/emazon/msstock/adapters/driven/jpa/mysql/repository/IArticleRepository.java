package com.emazon.msstock.adapters.driven.jpa.mysql.repository;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.emazon.msstock.adapters.driven.jpa.mysql.util.ConstantsQuerys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long>  {
    Optional<ArticleEntity> findByName(String name);
    Optional<ArticleEntity> findById(Long id);

    @Query(ConstantsQuerys.FIND_ALL_ORDER_BY_FIRST_CATEGORY_NAME_ASC)
    Page<ArticleEntity> findAllOrderByNumberOfCategoriesAsc(Pageable pageable);
    @Query(ConstantsQuerys.FIND_ALL_ORDER_BY_FIRST_CATEGORY_NAME_DESC)
    Page<ArticleEntity> findAllOrderByNumberOfCategoriesDesc(Pageable pageable);

    @Query(ConstantsQuerys.FIND_ARTICLES_CART_BY_CATEGORY_BRAND_NAME)
    Page<ArticleEntity> findByCategoryNameAndBrandName(@Param("articleIds") List<Long> articleIds, @Param("categoryName") String categoryName, @Param("brandName") String brandName, Pageable pageable);

    @Query(ConstantsQuerys.FIND_ARTICLES_CART_BY_CATEGORY_NAME)
    Page<ArticleEntity> findByCategoryName(@Param("articleIds") List<Long> articleIds, @Param("categoryName") String categoryName, Pageable pageable);

    @Query(ConstantsQuerys.FIND_ARTICLES_CART_BY_BRAND_NAME)
    Page<ArticleEntity> findByBrandName(@Param("articleIds") List<Long> articleIds, @Param("brandName") String brandName, Pageable pageable);

    Page<ArticleEntity> findByIdIn(List<Long> articleIds, Pageable pageable);

}
