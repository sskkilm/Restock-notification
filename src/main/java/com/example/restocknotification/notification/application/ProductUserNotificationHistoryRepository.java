package com.example.restocknotification.notification.application;

import com.example.restocknotification.notification.domain.ProductUserNotificationHistory;

public interface ProductUserNotificationHistoryRepository {

    void save(ProductUserNotificationHistory productUserHistory);

}
