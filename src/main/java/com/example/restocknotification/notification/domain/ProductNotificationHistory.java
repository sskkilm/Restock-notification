package com.example.restocknotification.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.example.restocknotification.notification.domain.ProductNotificationStatus.IN_PROGRESS;

@Getter
@AllArgsConstructor
@Builder
public class ProductNotificationHistory {

    private Long id;

    private Long productId;

    private int restockCount;

    private int stock;

    private ProductNotificationStatus status;

    private Long lastUserId;

    public static ProductNotificationHistory create(Long productId, int restockCount, int stock) {
        return new ProductNotificationHistory(null, productId, restockCount, stock, IN_PROGRESS, null);
    }

    public void updateStatus(ProductNotificationStatus status) {
        this.status = status;
    }

    public void updateLastUserId(Long lastUserId) {
        this.lastUserId = lastUserId;
    }
}
