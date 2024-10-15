package com.example.restocknotification.productusernotification.infrastructure.entity;

import com.example.restocknotification.product.infrastructure.entity.ProductEntity;
import com.example.restocknotification.productusernotification.domain.ProductUserNotification;
import com.example.restocknotification.user.domain.UserEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductUserNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private boolean isActivated;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public ProductUserNotification toModel() {
        return ProductUserNotification.builder()
                .productId(this.productEntity.getId())
                .userId(this.userEntity.getId())
                .isActivated(this.isActivated)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
