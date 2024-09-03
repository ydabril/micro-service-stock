package com.emazon.msstock.domain.exception;

public class InvalidCategoryCountException extends RuntimeException {
    public InvalidCategoryCountException(int message) {
        super(String.valueOf(message));
    }

}
