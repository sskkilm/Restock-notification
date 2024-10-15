package com.example.restocknotification.notification.application;

import com.example.restocknotification.common.exception.SoldOutException;
import com.example.restocknotification.common.observer.ProductSoldOutObserver;
import com.example.restocknotification.notification.domain.ProductNotificationHistory;
import com.example.restocknotification.notification.domain.ProductUserNotificationHistory;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.restocknotification.notification.domain.ProductNotificationStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;
    private final ProductSoldOutObserver observer;
    private final Bucket bucket;

    public void sendNotifications(Long productId, int restockCount, int stock, List<Long> userIds) {
        ProductNotificationHistory productHistory = ProductNotificationHistory.create(productId, restockCount, stock);

        Long lastUserId = null;
        while (!userIds.isEmpty()) {
            try {
                lastUserId = sendNotification(productId, restockCount, stock, userIds);
            } catch (SoldOutException e) {
                productHistory.updateStatus(CANCELED_BY_SOLD_OUT);
                break;
            } catch (Exception e) {
                productHistory.updateStatus(CANCELED_BY_ERROR);
                break;
            }
        }

        if (productHistory.getStatus() == IN_PROGRESS) {
            productHistory.updateStatus(COMPLETED);
        }
        productHistory.updateLastUserId(lastUserId);
        productNotificationHistoryRepository.save(productHistory);
    }

    public Long findLastUserId(Long productId) {
        ProductNotificationHistory productHistory = productNotificationHistoryRepository.findByProductId(productId);
        return productHistory.getLastUserId();
    }

    private Long sendNotification(Long productId, int restockCount, int stock, List<Long> userIds) {
        if (observer.isSoldOutProduct(productId)) {
            throw new SoldOutException("product sold out");
        }

        Long userId = null;
        if (bucket.tryConsume(1)) {

            userId = userIds.remove(0);
            ProductUserNotificationHistory userHistory = ProductUserNotificationHistory.create(
                    productId, restockCount, stock, userId
            );
            productUserNotificationHistoryRepository.save(userHistory);
        }

        return userId;
    }

}
