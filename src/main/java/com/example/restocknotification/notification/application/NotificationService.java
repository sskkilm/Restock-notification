package com.example.restocknotification.notification.application;

import com.example.restocknotification.common.exception.SoldOutException;
import com.example.restocknotification.common.observer.SoldOutProductObserver;
import com.example.restocknotification.notification.application.repository.ProductNotificationHistoryRepository;
import com.example.restocknotification.notification.application.repository.ProductUserNotificationHistoryRepository;
import com.example.restocknotification.notification.application.repository.ProductUserNotificationRepository;
import com.example.restocknotification.notification.domain.NotificationSender;
import com.example.restocknotification.notification.domain.entity.ProductNotificationHistory;
import com.example.restocknotification.notification.domain.entity.ProductUserNotification;
import com.example.restocknotification.notification.domain.entity.ProductUserNotificationHistory;
import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.restocknotification.notification.domain.ProductNotificationStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;
    private final NotificationSender notificationSender;
    private final SoldOutProductObserver soldOutProductObserver;

    private static final int BATCH_SIZE = 500;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendRestockNotification(Product product) {
        ProductNotificationHistory productHistory = ProductNotificationHistory.create(product, IN_PROGRESS);

        List<User> users = getUsersForSendingRestockNotification(product);
        try {
            sendRestockNotificationToUsers(product, users);

            productHistory.updateStatus(COMPLETED);
        } catch (SoldOutException e) {
            productHistory.updateStatus(CANCELED_BY_SOLD_OUT);
        } catch (Exception e) {
            productHistory.updateStatus(CANCELED_BY_ERROR);
        } finally {
            productNotificationHistoryRepository.save(productHistory);
        }
    }

    @Transactional
    public void resendRestockNotification(Product product) {
        ProductNotificationHistory productHistory = productNotificationHistoryRepository.findByProduct(product);

        Long lastReceivedUserId = getLastReceivedUserId(product);
        List<User> nonReceivedUsers = getUsersWhoHaveNotReceivedNotification(product, lastReceivedUserId);

        try {
            sendRestockNotificationToUsers(product, nonReceivedUsers);

            productHistory.updateStatus(COMPLETED);
        } catch (SoldOutException e) {
            productHistory.updateStatus(CANCELED_BY_SOLD_OUT);
        } catch (Exception e) {
            productHistory.updateStatus(CANCELED_BY_ERROR);
        }
    }

    private void sendRestockNotificationToUsers(Product product, List<User> users) throws InterruptedException {
        long rateLimitStartTime = System.currentTimeMillis();

        int sentCount = 0;
        for (User user : users) {
            if (soldOutProductObserver.isSoldOutProduct(product)) {
                soldOutProductObserver.productStatusInit(product);
                throw new SoldOutException("this product sold out");
            }

            boolean isSent = notificationSender.run(product, user);
            if (isSent) {
                sentCount++;
            }

            ProductUserNotificationHistory productUserHistory = ProductUserNotificationHistory.create(product, user);
            productUserNotificationHistoryRepository.save(productUserHistory);

            if (sentCount % BATCH_SIZE == 0) {
                long rateLimitEndTime = System.currentTimeMillis();

                long runningTime = rateLimitEndTime - rateLimitStartTime;
                if (runningTime < 1000) {
                    Thread.sleep(1000 - runningTime);
                }

                rateLimitStartTime = System.currentTimeMillis();
            }
        }
    }

    private List<User> getUsersForSendingRestockNotification(Product product) {
        List<ProductUserNotification> productUserNotifications = productUserNotificationRepository.findAllByProduct(product);
        return productUserNotifications.stream().map(ProductUserNotification::getUser).toList();
    }

    private Long getLastReceivedUserId(Product product) {
        List<User> receivedUsers = getUsersWhoReceivedNotification(product);
        if (receivedUsers.isEmpty()) {
            return -1L;
        }

        return receivedUsers.get(receivedUsers.size() - 1).getId();
    }

    private List<User> getUsersWhoReceivedNotification(Product product) {
        List<ProductUserNotificationHistory> productUserNotificationHistories =
                productUserNotificationHistoryRepository.findAllByProduct(product);
        return productUserNotificationHistories.stream().map(ProductUserNotificationHistory::getUser).toList();
    }

    private List<User> getUsersWhoHaveNotReceivedNotification(Product product, Long lastReceivedUserId) {
        List<ProductUserNotification> productUserNotifications =
                productUserNotificationRepository
                        .findAllByProductAndUserIdGreaterThan(product, lastReceivedUserId);
        return productUserNotifications.stream().map(ProductUserNotification::getUser).toList();
    }
}
