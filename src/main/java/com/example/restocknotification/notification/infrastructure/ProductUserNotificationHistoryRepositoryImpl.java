package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.notification.application.ProductUserNotificationHistoryRepository;
import com.example.restocknotification.notification.domain.ProductUserNotificationHistory;
import com.example.restocknotification.notification.infrastructure.entity.ProductUserNotificationHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository {

    private final ProductUserNotificationHistoryJpaRepository productUserNotificationHistoryJpaRepository;

    @Override
    public void save(ProductUserNotificationHistory productUserHistory) {
        productUserNotificationHistoryJpaRepository.save(
                ProductUserNotificationHistoryEntity.toEntity(productUserHistory)
        );
    }
}
