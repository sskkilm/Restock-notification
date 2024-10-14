package com.example.restocknotification.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long restockCount;

    private Long stock;

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
            stock = 0L;
        }
    }
}
