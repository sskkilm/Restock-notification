package com.example.restocknotification.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class ProductDto {

    private final Long productId;
    private final int restockCount;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ProductDto that = (ProductDto) object;
        return restockCount == that.restockCount && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, restockCount);
    }
}
