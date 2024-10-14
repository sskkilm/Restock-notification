package com.example.restocknotification.common.eventlistener;

import com.example.restocknotification.notification.application.NotificationService;
import com.example.restocknotification.product.domain.ProductRestockNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductRestockEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendRestockNotification(ProductRestockNotificationEvent event) {
        notificationService.sendRestockNotification(event.product());
    }

}
