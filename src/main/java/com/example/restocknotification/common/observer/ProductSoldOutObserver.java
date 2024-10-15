package com.example.restocknotification.common.observer;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class ProductSoldOutObserver {

    private final ConcurrentHashMap<Long, Boolean> soldOutProducts;

    public void save(Long productId) {
        soldOutProducts.put(productId, true);
    }

    public boolean isSoldOutProduct(Long productId) {
        return soldOutProducts.getOrDefault(productId, false);
    }

    public void remove(Long productId) {
        soldOutProducts.remove(productId);
    }
}
