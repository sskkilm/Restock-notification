package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.notification.domain.entity.ProductUserNotification;
import com.example.restocknotification.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserNotificationJpaRepository extends JpaRepository<ProductUserNotification, Long> {

    List<ProductUserNotification> findAllByProduct(Product product);

    @Query("select pun from ProductUserNotification pun where pun.product = :product and pun.user.id > :userId")
    List<ProductUserNotification> findAllByProductAndUserIdGreaterThan(
            @Param("product") Product product, @Param("userId") Long userId
    );

}
