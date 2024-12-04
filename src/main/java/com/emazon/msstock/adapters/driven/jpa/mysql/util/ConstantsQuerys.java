package com.emazon.msstock.adapters.driven.jpa.mysql.util;

public class ConstantsQuerys {
    private ConstantsQuerys() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String FIND_ALL_ORDER_BY_FIRST_CATEGORY_NAME_ASC =
            "SELECT a FROM ArticleEntity a " +
                    "JOIN a.categories c " +
                    "GROUP BY a.id " +
                    "ORDER BY MIN(c.name) ASC";
    public static final String FIND_ALL_ORDER_BY_FIRST_CATEGORY_NAME_DESC =
            "SELECT a FROM ArticleEntity a " +
                    "JOIN a.categories c " +
                    "GROUP BY a.id " +
                    "ORDER BY MIN(c.name) DESC";

    public static final String FIND_ARTICLES_CART_BY_CATEGORY_NAME =
            "SELECT a FROM ArticleEntity a JOIN a.categories c WHERE c.name = :categoryName AND a.id IN :articleIds";

    public static final String FIND_ARTICLES_CART_BY_BRAND_NAME =
            "SELECT a FROM ArticleEntity a WHERE a.brand.name = :brandName AND a.id IN :articleIds";

    public static final String FIND_ARTICLES_CART_BY_CATEGORY_BRAND_NAME =
            "SELECT a FROM ArticleEntity a JOIN a.categories c WHERE a.brand.name = :brandName AND c.name = :categoryName AND a.id IN :articleIds";
}
