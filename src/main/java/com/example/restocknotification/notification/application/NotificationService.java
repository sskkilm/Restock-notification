package com.example.restocknotification.notification.application;

import com.example.restocknotification.common.exception.SoldOutException;
import com.example.restocknotification.common.observer.SoldOutProductObserver;
import com.example.restocknotification.notification.application.repository.ProductNotificationHistoryRepository;
import com.example.restocknotification.notification.application.repository.ProductUserNotificationHistoryRepository;
import com.example.restocknotification.notification.application.repository.ProductUserNotificationRepository;
import com.example.restocknotification.notification.domain.NotificationDetails;
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

        List<User> users = getActivatedUsersForSendingRestockNotification(product);

        NotificationDetails details = new NotificationDetails();
        try {
            sendRestockNotificationToUsers(product, users, details);

            details.updateStatus(COMPLETED);
        } catch (SoldOutException e) {
            log.info("{}", "notification canceled: " + CANCELED_BY_SOLD_OUT);

            details.updateStatus(CANCELED_BY_SOLD_OUT);
        } catch (Exception e) {
            log.info("{}", "notification canceled: " + CANCELED_BY_ERROR);

            details.updateStatus(CANCELED_BY_ERROR);
        } finally {
            productHistory.updateDetails(details);

            productNotificationHistoryRepository.save(productHistory);
        }
    }

    @Transactional
    public void resendRestockNotification(Product product) {
        ProductNotificationHistory productHistory = productNotificationHistoryRepository.findByProduct(product);

        Long lastReceivedUserId = productHistory.getLastReceivedUserId();
        List<User> users = getActivatedUsersWhoHaveNotReceivedNotification(product, lastReceivedUserId);

        NotificationDetails details = new NotificationDetails();
        try {
            sendRestockNotificationToUsers(product, users, details);

            details.updateStatus(COMPLETED);
        } catch (SoldOutException e) {
            log.info("{}", "notification canceled: " + CANCELED_BY_SOLD_OUT);

            details.updateStatus(CANCELED_BY_SOLD_OUT);
        } catch (Exception e) {
            log.info("{}", "notification canceled: " + CANCELED_BY_ERROR);

            details.updateStatus(CANCELED_BY_ERROR);
        } finally {
            productHistory.updateDetails(details);
        }
    }

    private void sendRestockNotificationToUsers(Product product, List<User> users, NotificationDetails details) throws InterruptedException {
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

            details.updateLastReceivedUserId(user.getId());

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

    private List<User> getActivatedUsersForSendingRestockNotification(Product product) {
        List<ProductUserNotification> productUserNotifications = productUserNotificationRepository.findAllByProductAndActivated(product);
        return productUserNotifications.stream().map(ProductUserNotification::getUser).toList();
    }

    private List<User> getActivatedUsersWhoHaveNotReceivedNotification(Product product, Long lastReceivedUserId) {
        List<ProductUserNotification> productUserNotifications =
                productUserNotificationRepository
                        .findAllByProductAndActivatedUserIdGreaterThan(product, lastReceivedUserId);
        return productUserNotifications.stream().map(ProductUserNotification::getUser).toList();
    }
}
