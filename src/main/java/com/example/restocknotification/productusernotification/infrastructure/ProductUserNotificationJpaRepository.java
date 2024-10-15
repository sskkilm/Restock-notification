package com.example.restocknotification.productusernotification.infrastructure;

import com.example.restocknotification.productusernotification.infrastructure.entity.ProductUserNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserNotificationJpaRepository extends JpaRepository<ProductUserNotificationEntity, Long> {

    @Query("select pune " +
            "from ProductUserNotificationEntity pune " +
            "where pune.productEntity.id = :productId " +
            "and pune.isActivated = true")
    List<ProductUserNotificationEntity> findAllByProductIdAndActivated(@Param("productId") Long productId);

}
