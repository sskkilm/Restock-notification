package com.example.restocknotification.productusernotification.application;

import com.example.restocknotification.productusernotification.domain.ProductUserNotification;

import java.util.List;

public interface ProductUserNotificationRepository {

    List<ProductUserNotification> findAllByProductIdAndActivated(Long productId);

    List<ProductUserNotification> findAllByProductIdAndActivatedGreaterThanLastUserId(Long productId, Long lastUserId);
}
