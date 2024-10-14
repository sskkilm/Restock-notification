package com.example.restocknotification.notification.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductNotificationStatus {
    IN_PROGRESS ("발송 중"),
    CANCELED_BY_SOLD_OUT ("품절에 의한 발송 중단"),
    CANCELED_BY_ERROR ("예외에 의한 발송 중단"),
    COMPLETED ("완료"),
    ;

    private final String message;
}
