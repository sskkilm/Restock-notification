package com.example.restocknotification.product.api;

import com.example.restocknotification.product.application.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public void restock(@PathVariable Long productId) {
        productService.restock(productId);
    }

    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public void resendRestockNotification(@PathVariable Long productId) {
        productService.resendRestockNotification(productId);
    }

}
