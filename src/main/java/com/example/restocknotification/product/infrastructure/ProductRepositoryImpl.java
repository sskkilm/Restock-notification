package com.example.restocknotification.product.infrastructure;

import com.example.restocknotification.product.application.ProductRepository;
import com.example.restocknotification.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product findById(Long id) {
        return productJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product not found"));
    }
    
}
