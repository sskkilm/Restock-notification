package com.example.restocknotification.product.application;

import com.example.restocknotification.product.domain.Product;

public interface ProductRepository {

    Product findById(Long id);

}
