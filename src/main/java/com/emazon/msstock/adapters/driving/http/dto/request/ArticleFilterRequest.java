package com.emazon.msstock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ArticleFilterRequest {
    private final List<Long> articleIds;
    private final String sortDirection;
    private final Integer page;
    private final Integer size;
    private final String categoryName;
    private final String brandName;
}
