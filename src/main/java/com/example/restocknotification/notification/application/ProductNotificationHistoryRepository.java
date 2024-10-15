package com.example.restocknotification.notification.application;

import com.example.restocknotification.notification.domain.ProductNotificationHistory;

public interface ProductNotificationHistoryRepository {

    void save(ProductNotificationHistory history);

    ProductNotificationHistory findByProductId(Long productId);
}
