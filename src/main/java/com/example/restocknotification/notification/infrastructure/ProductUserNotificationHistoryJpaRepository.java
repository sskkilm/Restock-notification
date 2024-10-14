package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.notification.domain.entity.ProductUserNotificationHistory;
import com.example.restocknotification.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserNotificationHistoryJpaRepository extends JpaRepository<ProductUserNotificationHistory, Long> {

    List<ProductUserNotificationHistory> findAllByProduct(Product product);
}
