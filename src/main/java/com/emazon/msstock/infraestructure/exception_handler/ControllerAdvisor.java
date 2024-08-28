package com.emazon.msstock.infraestructure.exception_handler;

import com.emazon.msstock.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.emazon.msstock.domain.exception.CategoryAlreadyExistsException;
import com.emazon.msstock.infraestructure.Constants;
import com.emazon.msstock.domain.exception.EmptyFieldException;
import com.emazon.msstock.domain.exception.LengthFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.EMPTY_FIELD_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(LengthFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(LengthFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.FIELD_LENGTH_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleSupplierAlreadyExistsException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoDataFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                Constants.NO_DATA_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));
    }
}
