package com.example.restocknotification.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {

    private Long id;

    private int restockCount;

    private int stock;

    public void increaseRestockCount() {
        this.restockCount++;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    public boolean isSoldOut() {
        return stock == 0L;
    }

    public void decreaseStock(Long quantity) {
        this.stock -= quantity;
        if (stock <= 0) {
            stock = 0;
        }
    }
}
