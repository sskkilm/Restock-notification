package com.example.restocknotification.notification.application.repository;

import com.example.restocknotification.notification.domain.entity.ProductUserNotification;
import com.example.restocknotification.product.domain.Product;

import java.util.List;

public interface ProductUserNotificationRepository {

    List<ProductUserNotification> findAllByProduct(Product product);

    List<ProductUserNotification> findAllByProductAndUserIdGreaterThan(Product product, Long lastReceivedUserId);
}
