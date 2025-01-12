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
}
