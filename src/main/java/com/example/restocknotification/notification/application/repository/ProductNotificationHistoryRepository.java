package com.example.restocknotification.notification.application.repository;

import com.example.restocknotification.notification.domain.entity.ProductNotificationHistory;
import com.example.restocknotification.product.domain.Product;

public interface ProductNotificationHistoryRepository {

    void save(ProductNotificationHistory history);

    ProductNotificationHistory findByProduct(Product product);
}
