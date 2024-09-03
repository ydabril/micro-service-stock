package com.emazon.msstock.domain.exception;

public class NegativeNotAllowedException extends  RuntimeException{
    public NegativeNotAllowedException(String message) {
        super(message);
    }
}
