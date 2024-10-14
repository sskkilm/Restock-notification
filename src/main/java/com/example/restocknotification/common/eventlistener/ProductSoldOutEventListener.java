package com.example.restocknotification.common.eventlistener;

import com.example.restocknotification.common.observer.SoldOutProductObserver;
import com.example.restocknotification.product.domain.ProductSoldOutEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductSoldOutEventListener {

    private final SoldOutProductObserver soldOutProductObserver;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void saveSoldOutProduct(ProductSoldOutEvent event) {
        soldOutProductObserver.save(event.product());
    }

}
