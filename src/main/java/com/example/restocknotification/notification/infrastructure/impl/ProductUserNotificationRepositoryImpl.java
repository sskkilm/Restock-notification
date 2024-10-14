package com.example.restocknotification.notification.infrastructure.impl;

import com.example.restocknotification.notification.application.repository.ProductUserNotificationRepository;
import com.example.restocknotification.notification.domain.entity.ProductUserNotification;
import com.example.restocknotification.notification.infrastructure.ProductUserNotificationJpaRepository;
import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationRepositoryImpl implements ProductUserNotificationRepository {

    private final ProductUserNotificationJpaRepository productUserNotificationJpaRepository;

    @Override
    public List<User> getUsersForRestockNotification(Product product) {
        return productUserNotificationJpaRepository.findAllByProduct(product)
                .stream().map(ProductUserNotification::getUser).toList();
    }

}
