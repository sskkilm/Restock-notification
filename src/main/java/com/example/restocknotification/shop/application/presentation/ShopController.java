package com.example.restocknotification.shop.application.presentation;

import com.example.restocknotification.shop.application.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public void restock(@PathVariable Long productId) {
        shopService.restock(productId);
    }

    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public void resendNotification(@PathVariable Long productId) {
        shopService.resendNotification(productId);
    }

}
