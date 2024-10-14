package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.notification.domain.entity.ProductNotificationHistory;
import com.example.restocknotification.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductNotificationHistoryJpaRepository extends JpaRepository<ProductNotificationHistory, Long> {

    Optional<ProductNotificationHistory> findByProduct(Product product);

}
