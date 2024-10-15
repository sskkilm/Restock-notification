package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.notification.application.ProductNotificationHistoryRepository;
import com.example.restocknotification.notification.domain.ProductNotificationHistory;
import com.example.restocknotification.notification.infrastructure.entity.ProductNotificationHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductNotificationHistoryRepositoryImpl implements ProductNotificationHistoryRepository {

    private final ProductNotificationHistoryJpaRepository productNotificationHistoryJpaRepository;

    @Override
    public void save(ProductNotificationHistory history) {
        productNotificationHistoryJpaRepository.save(ProductNotificationHistoryEntity.toEntity(history));
    }

    @Override
    public ProductNotificationHistory findByProductId(Long productId) {
        ProductNotificationHistoryEntity productHistoryEntity = productNotificationHistoryJpaRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("product notification history not found"));
        return productHistoryEntity.toModel();
    }
}
