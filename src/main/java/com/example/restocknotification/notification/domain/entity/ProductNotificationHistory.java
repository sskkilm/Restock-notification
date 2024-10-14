package com.example.restocknotification.notification.domain.entity;

import com.example.restocknotification.notification.domain.ProductNotificationStatus;
import com.example.restocknotification.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ProductNotificationStatus status;

    private Long lastReceivedUserId;

    public static ProductNotificationHistory create(Product product, ProductNotificationStatus status) {
        return new ProductNotificationHistory(null, product, status, null);
    }

    public void updateStatus(ProductNotificationStatus status) {
        this.status = status;
    }

}
