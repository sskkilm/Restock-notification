package com.example.restocknotification.product.infrastructure.entity;

import com.example.restocknotification.product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int restockCount;

    private int stock;

    public Product toModel() {
        return Product.builder()
                .id(this.id)
                .restockCount(this.restockCount)
                .stock(this.stock)
                .build();
    }
}
