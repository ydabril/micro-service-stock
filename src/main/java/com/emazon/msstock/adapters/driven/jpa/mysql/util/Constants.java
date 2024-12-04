package com.emazon.msstock.adapters.driven.jpa.mysql.util;

public class Constants {
    public static final String NAME = "name";
    public  static  final String DESCRIPTION = "description";
    public  static  final  String SORT_DIRECTION_ASC = "ASC";
    public  static  final  String SORT_DIRECTION_DESC = "DESC";
    public  static  final String CATEGORIES = "CATEGORIES";

    public static final String PARAM_ARTICLE_ID = "articleIds";

    public enum SortBy {
        ARTICLE_NAME("name"),
        BRAND_NAME("brand.name");


        private final String fieldName;

        SortBy(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }
    }
}
