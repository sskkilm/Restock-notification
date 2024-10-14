package com.example.restocknotification.notification.infrastructure.impl;

import com.example.restocknotification.notification.application.repository.ProductUserNotificationHistoryRepository;
import com.example.restocknotification.notification.domain.entity.ProductUserNotificationHistory;
import com.example.restocknotification.notification.infrastructure.ProductUserNotificationHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository {

    private final ProductUserNotificationHistoryJpaRepository productUserNotificationHistoryJpaRepository;

    @Override
    public void save(ProductUserNotificationHistory productUserHistory) {
        productUserNotificationHistoryJpaRepository.save(productUserHistory);
    }

}
