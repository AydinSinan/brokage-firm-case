package com.pm.brokeragefirm.repository;

import com.pm.brokeragefirm.entity.Order;
import com.pm.brokeragefirm.enums.OrderSide;
import com.pm.brokeragefirm.enums.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Instant;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepo;


    @Test
    void saveAndLoad() {
        Order o = Order.builder().customerId(1L).assetName("AAPL").orderSide(OrderSide.BUY)
                .size(new BigDecimal("10")) .price(new BigDecimal("500"))
                .status(OrderStatus.PENDING) .createDate(Instant.now()).build();
        Order saved = orderRepo.save(o);
        Assertions.assertNotNull(saved.getId());
    }
}
