package com.example.restocknotification.shop.application;

import com.example.restocknotification.notification.application.NotificationService;
import com.example.restocknotification.product.application.ProductService;
import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.productusernotification.application.ProductUserNotificationService;
import com.example.restocknotification.productusernotification.domain.ProductUserNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ProductService productService;
    private final ProductUserNotificationService productUserNotificationService;
    private final NotificationService notificationService;

    public void restock(Long productId) {
        Product product = productService.restock(productId);
        List<Long> userIds = getActivatedUserIds(product);
        notificationService.sendNotifications(product.getId(), product.getRestockCount(), product.getStock(), userIds);
    }

    public void resendNotification(Long productId) {
        Product product = productService.findById(productId);
        Long lastUserId = notificationService.findLastUserId(product.getId());
        List<Long> userIds = getActivatedUserIdsGreaterThanLastUserId(product, lastUserId);
        notificationService.sendNotifications(product.getId(), product.getRestockCount(), product.getStock(), userIds);
    }

    private List<Long> getActivatedUserIds(Product product) {
        List<ProductUserNotification> productUserNotifications = productUserNotificationService.findAllByProductIdAndActivated(product.getId());
        return productUserNotifications.stream().map(ProductUserNotification::getUserId).toList();
    }

    private List<Long> getActivatedUserIdsGreaterThanLastUserId(Product product, Long lastUserId) {
        List<ProductUserNotification> productUserNotifications = productUserNotificationService.findAllByProductIdAndActivatedGreaterThanLastUserId(product.getId(), lastUserId);
        return productUserNotifications.stream().map(ProductUserNotification::getUserId).toList();
    }

}
