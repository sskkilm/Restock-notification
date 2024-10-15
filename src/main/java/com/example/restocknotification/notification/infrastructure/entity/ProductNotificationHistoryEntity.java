package com.example.restocknotification.notification.infrastructure.entity;

import com.example.restocknotification.notification.domain.ProductNotificationHistory;
import com.example.restocknotification.notification.domain.ProductNotificationStatus;
import com.example.restocknotification.product.infrastructure.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProductNotificationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Enumerated(EnumType.STRING)
    private ProductNotificationStatus status;

    private Long lastUserId;

    public static ProductNotificationHistoryEntity toEntity(ProductNotificationHistory productNotificationHistory) {
        return ProductNotificationHistoryEntity.builder()
                .productEntity(
                        ProductEntity.builder()
                                .id(productNotificationHistory.getProductId())
                                .restockCount(productNotificationHistory.getRestockCount())
                                .stock(productNotificationHistory.getStock())
                                .build()
                )
                .status(productNotificationHistory.getStatus())
                .lastUserId(productNotificationHistory.getLastUserId())
                .build();
    }

    public ProductNotificationHistory toModel() {
        return ProductNotificationHistory.builder()
                .id(this.id)
                .productId(this.productEntity.getId())
                .restockCount(this.productEntity.getRestockCount())
                .stock(this.productEntity.getStock())
                .status(this.status)
                .lastUserId(this.lastUserId)
                .build();
    }
}
