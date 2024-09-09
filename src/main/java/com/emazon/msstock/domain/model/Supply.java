package com.emazon.msstock.domain.model;

public class Supply {
    private final Long articleId;
    private final Long quantity;

    public Supply(Long articleId, Long quantity) {
        this.articleId = articleId;
        this.quantity = quantity;
    }

    public Long getArticleId() {
        return articleId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
