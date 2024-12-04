package com.emazon.msstock.domain.util;

public final class DomainConstants {

    public enum Field {
        NAME,
        DESCRIPTION
    }

    public enum FieldArticle {
        NAME,
        PRICE,
        QUANTITY,
        CATEGORIES
    }
    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTIOM_NULL_MESSAGE = "Field 'description' cannot be null";

    public static final int MAXIMUM_LENGTH_NAME = 50;

    public static final int MAXIMUM_LENGTH_DESCRIPTION = 90;

    public static final int MAXIMUM_LENGTH_NAME_BRAND = 50;

    public static final int MAXIMUM_LENGTH_DESCRIPTION_BRAND = 120;

    public static final int MINIMUM_COUNT_CATEGORY = 1;

    public static final int MAXIMUM_COUNT_CATEGORY = 3;

    public static final int MIN_QUANTITY= 0;


    public static final String FIELD_PRICE_NULL_MESSAGE = "Field 'price' cannot be null";
    public static final String FIELD_QUANTITY_NULL_MESSAGE = "Field 'quantity' cannot be null";
    public static final String FIELD_BRAND_NULL_MESSAGE = "Field 'brand' cannot be null";
}
