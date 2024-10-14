package com.example.restocknotification.notification.infrastructure.impl;

import com.example.restocknotification.notification.application.repository.ProductNotificationHistoryRepository;
import com.example.restocknotification.notification.domain.entity.ProductNotificationHistory;
import com.example.restocknotification.notification.infrastructure.ProductNotificationHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductNotificationHistoryRepositoryImpl implements ProductNotificationHistoryRepository {

    private final ProductNotificationHistoryJpaRepository productNotificationHistoryJpaRepository;

    @Override
    public void save(ProductNotificationHistory history) {
        productNotificationHistoryJpaRepository.save(history);
    }
}
