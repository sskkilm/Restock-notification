package com.example.restocknotification.productusernotification.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductUserNotification {

    private Long productId;

    private Long userId;

    private boolean isActivated;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
