package com.example.restocknotification.productusernotification.infrastructure;

import com.example.restocknotification.notification.infrastructure.ProductUserNotificationQueryRepository;
import com.example.restocknotification.productusernotification.application.ProductUserNotificationRepository;
import com.example.restocknotification.productusernotification.domain.ProductUserNotification;
import com.example.restocknotification.productusernotification.infrastructure.entity.ProductUserNotificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationRepositoryImpl implements ProductUserNotificationRepository {

    private final ProductUserNotificationJpaRepository productUserNotificationJpaRepository;
    private final ProductUserNotificationQueryRepository productUserNotificationQueryRepository;

    @Override
    public List<ProductUserNotification> findAllByProductIdAndActivated(Long productId) {
        return productUserNotificationJpaRepository.findAllByProductIdAndActivated(productId)
                .stream().map(ProductUserNotificationEntity::toModel).toList();

    }

    @Override
    public List<ProductUserNotification> findAllByProductIdAndActivatedGreaterThanLastUserId(Long productId, Long lastUserId) {
        return productUserNotificationQueryRepository.findAllByProductIdAndActivatedGreaterThanLastUserId(productId, lastUserId)
                .stream().map(ProductUserNotificationEntity::toModel).toList();
    }

}
