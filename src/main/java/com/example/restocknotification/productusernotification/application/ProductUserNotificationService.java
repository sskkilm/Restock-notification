package com.example.restocknotification.productusernotification.application;

import com.example.restocknotification.productusernotification.domain.ProductUserNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUserNotificationService {

    private final ProductUserNotificationRepository productUserNotificationRepository;

    public List<ProductUserNotification> findAllByProductIdAndActivated(Long productId) {
        return productUserNotificationRepository.findAllByProductIdAndActivated(productId);
    }

    public List<ProductUserNotification> findAllByProductIdAndActivatedGreaterThanLastUserId(Long productId, Long lastUserId) {
        return productUserNotificationRepository.findAllByProductIdAndActivatedGreaterThanLastUserId(productId, lastUserId);
    }
}
