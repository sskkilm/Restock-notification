package com.example.restocknotification.common.eventlistener;

import com.example.restocknotification.notification.application.NotificationService;
import com.example.restocknotification.product.domain.ResendRestockNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResendRestockNotificationEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void resendRestockNotification(ResendRestockNotificationEvent event) {
        notificationService.resendRestockNotification(event.product());
    }

}
