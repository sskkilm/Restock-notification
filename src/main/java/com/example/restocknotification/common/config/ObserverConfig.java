package com.example.restocknotification.common.config;

import com.example.restocknotification.common.observer.ProductSoldOutObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ObserverConfig {

    @Bean
    public ProductSoldOutObserver soldOutProductObserver() {
        return new ProductSoldOutObserver(new ConcurrentHashMap<>());
    }

}
