package com.example.restocknotification.common.eventlistener;

import com.example.restocknotification.notification.application.NotificationService;
import com.example.restocknotification.product.domain.RestockNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RestockNotificationEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendRestockNotification(RestockNotificationEvent event) {
        notificationService.sendRestockNotification(event.product());
    }

}
