package com.example.restocknotification.product.application;

import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.product.domain.ProductRestockNotificationEvent;
import com.example.restocknotification.product.domain.ProductSoldOutEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void restock(Long productId) {
        Product product = productRepository.findById(productId);

        product.increaseRestockCount();
        // 재입고 수량 100으로 가정
        product.increaseStock(100);

        eventPublisher.publishEvent(new ProductRestockNotificationEvent(product));
    }

    @Transactional
    public void purchase(Long productId, Long quantity) {
        Product product = productRepository.findById(productId);

        product.decreaseStock(quantity);
        if (product.isSoldOut()) {
            eventPublisher.publishEvent(new ProductSoldOutEvent(product));
        }

    }

}
