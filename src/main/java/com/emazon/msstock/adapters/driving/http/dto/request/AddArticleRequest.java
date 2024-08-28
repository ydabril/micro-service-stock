package com.emazon.msstock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private final String name;
    private final BigDecimal price;
    private final Long quantity;
    private final Long brandId;
    private final List<Long> categoryIds;
}
