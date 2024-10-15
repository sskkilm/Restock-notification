package com.example.restocknotification.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductUserNotificationHistory {

    private Long id;

    private Long productId;

    private int restockCount;

    private int stock;

    private Long userId;

    private LocalDateTime createdAt;

    public static ProductUserNotificationHistory create(Long productId, int restockCount, int stock, Long userId) {
        return new ProductUserNotificationHistory(null, productId, restockCount, stock, userId, null);
    }

}
