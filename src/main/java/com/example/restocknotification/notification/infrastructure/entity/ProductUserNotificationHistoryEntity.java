package com.example.restocknotification.notification.infrastructure.entity;

import com.example.restocknotification.notification.domain.ProductUserNotificationHistory;
import com.example.restocknotification.product.infrastructure.entity.ProductEntity;
import com.example.restocknotification.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProductUserNotificationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static ProductUserNotificationHistoryEntity toEntity(ProductUserNotificationHistory productUserNotificationHistory) {
        return ProductUserNotificationHistoryEntity.builder()
                .productEntity(ProductEntity.builder()
                        .id(productUserNotificationHistory.getProductId())
                        .restockCount(productUserNotificationHistory.getRestockCount())
                        .stock(productUserNotificationHistory.getStock())
                        .build())
                .userEntity(UserEntity.builder().id(productUserNotificationHistory.getUserId()).build())
                .createdAt(productUserNotificationHistory.getCreatedAt())
                .build();
    }

}
