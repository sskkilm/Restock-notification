package com.example.restocknotification.notification.infrastructure;

import com.example.restocknotification.notification.domain.entity.ProductUserNotification;
import com.example.restocknotification.product.domain.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.restocknotification.notification.domain.entity.QProductUserNotification.productUserNotification;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public List<ProductUserNotification> findAllByProductAndActivatedUserIdGreaterThan(Product product, Long userId) {

        return jpaQueryFactory
                .selectFrom(productUserNotification)
                .where(productEq(product), userIdGreaterThan(userId), isActivated())
                .orderBy(productUserNotification.user.id.asc())
                .fetch();
    }

    private BooleanExpression productEq(Product product) {
        return product != null ? productUserNotification.product.eq(product) : null;
    }

    private BooleanExpression userIdGreaterThan(Long userId) {
        return userId != null ? productUserNotification.user.id.gt(userId) : null;
    }

    private BooleanExpression isActivated() {
        return productUserNotification.isActivated;
    }

}
