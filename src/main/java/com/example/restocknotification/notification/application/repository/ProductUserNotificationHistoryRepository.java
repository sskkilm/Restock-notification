package com.example.restocknotification.notification.application.repository;

import com.example.restocknotification.notification.domain.entity.ProductUserNotificationHistory;

public interface ProductUserNotificationHistoryRepository {

    void save(ProductUserNotificationHistory productUserHistory);
    
}
