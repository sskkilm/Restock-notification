package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.productusernotification.infrastructure.entity.ProductUserNotificationEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.restocknotification.productusernotification.infrastructure.entity.QProductUserNotificationEntity.productUserNotificationEntity;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public List<ProductUserNotificationEntity> findAllByProductIdAndActivatedGreaterThanLastUserId(Long productId, Long lastUserId) {
        return jpaQueryFactory
                .selectFrom(productUserNotificationEntity)
                .where(productIdEq(productId), userIdGreaterThan(lastUserId), isActivated())
                .fetch();
    }

    private BooleanExpression productIdEq(Long productId) {
        return productId != null ? productUserNotificationEntity.productEntity.id.eq(productId) : null;
    }

    private BooleanExpression userIdGreaterThan(Long lastUserId) {
        return lastUserId != null ? productUserNotificationEntity.userEntity.id.gt(lastUserId) : null;
    }

    private BooleanExpression isActivated() {
        return productUserNotificationEntity.isActivated;
    }
}
