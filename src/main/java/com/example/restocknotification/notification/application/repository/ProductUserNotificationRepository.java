package com.example.restocknotification.notification.application.repository;

import com.example.restocknotification.product.domain.Product;
import com.example.restocknotification.user.domain.User;

import java.util.List;

public interface ProductUserNotificationRepository {

    List<User> getUsersForRestockNotification(Product product);

}
