package com.emazon.msstock.infraestructure;

public class Constants {
    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String NO_DATA_FOUND_EXCEPTION_MESSAGE = "No data was found in the database";
    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE = "The element indicated does not exist";
    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The category you want to create already exists";

    public static final String BRAND_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The brand you want to create already exists";

    public static final String ARTICLE_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The article you want to create already exists";
    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";

    public static final String FIELD_LENGTH_EXCEPTION_MESSAGE = "the %s is too long";

    public static final String NEGATIVE_NOT_ALLOWED_EXCEPTION_MESSAGE = "Field %s can not receive negative values";

    public static final String DUPLICATE_CATEGORY_EXCEPTION_MESSAGE = "Articles cannot have repeated categories";

    public static final String INVALID_CATEGORT_COUNT_EXCEPTION = "Articles cannot have more than %s categories";

    public static final String CATEGORY_NO_DATA_FOUND_EXCEPTION_MESSAGE = "Category entered does not exist";

    public static final String ARTICLE_NO_DATA_FOUND_EXCEPTION_MESSAGE = "Article entered does not exist";

    public static final String ARTICLE_NO_STOCK = "There is not enough stock for one of the items";
}
