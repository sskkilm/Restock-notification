package com.example.restocknotification.notification.application.repository;

import com.example.restocknotification.notification.domain.entity.ProductUserNotificationHistory;
import com.example.restocknotification.product.domain.Product;

import java.util.List;

public interface ProductUserNotificationHistoryRepository {

    void save(ProductUserNotificationHistory productUserHistory);

    List<ProductUserNotificationHistory> findAllByProduct(Product product);
}
