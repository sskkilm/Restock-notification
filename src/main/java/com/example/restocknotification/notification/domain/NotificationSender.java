package com.example.restocknotification.notification.domain;

import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Notice Sender")
@Component
public class NotificationSender {

    public boolean run(Product product, User user) {
        // 항상 전송 성공한다고 가정
        log.info("Sending notification to User {} for Product {}", user.getId(), product.getId());
        return true;
    }
}
