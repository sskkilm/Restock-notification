package com.example.restocknotification.notification.domain;

import lombok.Getter;

@Getter
public class NotificationDetails {

    private Long lastReceivedUserId;
    private ProductNotificationStatus status;

    public void updateLastReceivedUserId(Long userId) {
        this.lastReceivedUserId = userId;
    }

    public void updateStatus(ProductNotificationStatus status) {
        this.status = status;
    }
}
