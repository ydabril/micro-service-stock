package com.emazon.msstock.domain.util;

public final class DomainConstants {

    public enum Field {
        NAME,
        DESCRIPTION
    }
    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTIOM_NULL_MESSAGE = "Field 'description' cannot be null";

    public static final int MAXIMUM_LENGTH_NAME = 50;

    public static final int MAXIMUM_LENGTH_DESCRIPTION = 90;
}
