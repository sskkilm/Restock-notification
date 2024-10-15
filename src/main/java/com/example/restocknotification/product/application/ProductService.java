package com.example.restocknotification.product.application;

import com.example.restocknotification.common.observer.ProductSoldOutObserver;
import com.example.restocknotification.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSoldOutObserver observer;

    @Transactional
    public Product restock(Long productId) {
        Product product = productRepository.findById(productId);

        product.increaseRestockCount();
        // 재입고 수량 100으로 가정
        product.increaseStock(100);

        observer.remove(product.getId());

        return product;
    }

    @Transactional
    public void purchase(Long productId, Long quantity) {
        Product product = productRepository.findById(productId);

        product.decreaseStock(quantity);
        if (product.isSoldOut()) {
            observer.save(productId);
        }

    }

    public Product findById(Long productId) {
        return productRepository.findById(productId);
    }
}
