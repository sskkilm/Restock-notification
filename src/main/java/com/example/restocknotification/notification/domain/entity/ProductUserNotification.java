package com.example.restocknotification.notification.domain.entity;

import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ProductUserNotification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
