package com.pm.brokeragefirm.service;

import com.pm.brokeragefirm.dto.request.CreateOrderRequest;
import com.pm.brokeragefirm.enums.OrderSide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    OrderService orderService;


    @Test
    void createBuy_reservesTry() {
        var resp = orderService.create(1L, new CreateOrderRequest("AAPL", OrderSide.BUY,
                new BigDecimal("10"), new BigDecimal("100"), "idem-1"));
        Assertions.assertEquals("AAPL", resp.assetName());
    }
}