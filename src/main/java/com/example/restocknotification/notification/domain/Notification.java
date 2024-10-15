package com.example.restocknotification.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Notification {
    private final Long productId;
    private final int restockCount;
    private final Long userId;
    private final ProductNotificationHistory productHistory;
}
