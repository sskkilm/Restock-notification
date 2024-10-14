package com.example.restocknotification.notification.infrastructure.impl;

import com.example.restocknotification.notification.application.repository.ProductUserNotificationRepository;
import com.example.restocknotification.notification.domain.entity.ProductUserNotification;
import com.example.restocknotification.notification.infrastructure.ProductUserNotificationJpaRepository;
import com.example.restocknotification.notification.infrastructure.ProductUserNotificationQueryRepository;
import com.example.restocknotification.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationRepositoryImpl implements ProductUserNotificationRepository {

    private final ProductUserNotificationJpaRepository productUserNotificationJpaRepository;
    private final ProductUserNotificationQueryRepository productUserNotificationQueryRepository;

    @Override
    public List<ProductUserNotification> findAllByProductAndActivated(Product product) {
        return productUserNotificationJpaRepository.findAllByProductAndActivated(product);
    }

    @Override
    public List<ProductUserNotification> findAllByProductAndActivatedUserIdGreaterThan(Product product, Long lastReceivedUserId) {
        return productUserNotificationQueryRepository.findAllByProductAndActivatedUserIdGreaterThan(product, lastReceivedUserId);
    }

}
