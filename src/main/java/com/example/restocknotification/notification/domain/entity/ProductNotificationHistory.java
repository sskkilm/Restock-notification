package com.example.restocknotification.notification.domain.entity;

import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.notification.domain.ProductNotificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductNotificationHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ProductNotificationStatus status;

    public static ProductNotificationHistory create(Product product, ProductNotificationStatus status) {
        return new ProductNotificationHistory(null, product, status);
    }

    public void updateStatus(ProductNotificationStatus status) {
        this.status = status;
    }

}
