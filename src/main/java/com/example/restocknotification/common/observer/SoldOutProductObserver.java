package com.example.restocknotification.common.observer;

import com.example.restocknotification.product.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class SoldOutProductObserver {

    private final ConcurrentHashMap<Long, Boolean> soldOutProducts;

    public void save(Product product) {
        soldOutProducts.put(product.getId(), true);
    }

    public boolean isSoldOutProduct(Product product) {
        return soldOutProducts.getOrDefault(product.getId(), false);
    }

    public void productStatusInit(Product product) {
        soldOutProducts.remove(product.getId());
    }
}
