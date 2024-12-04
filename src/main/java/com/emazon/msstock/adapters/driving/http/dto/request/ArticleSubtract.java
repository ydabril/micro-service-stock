package com.emazon.msstock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class ArticleSubtract {
    @NotNull(message = "Name cannot be null")
    private final Long articleId;
    private final String name;
    @NotNull(message = "quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private final long quantity;
}