package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.notification.infrastructure.entity.ProductNotificationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductNotificationHistoryJpaRepository extends JpaRepository<ProductNotificationHistoryEntity, Long> {

    @Query("select pnhe " +
            "from ProductUserNotificationEntity pnhe " +
            "where pnhe.productEntity.id = :productId")
    Optional<ProductNotificationHistoryEntity> findByProductId(@Param("productId") Long productId);
}
