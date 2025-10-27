package com.pm.brokeragefirm.dto.response;

import com.pm.brokeragefirm.enums.OrderSide;
import com.pm.brokeragefirm.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(Long id, Long customerId, String assetName, OrderSide side,
                            BigDecimal size, BigDecimal price, OrderStatus status, Instant createDate) {}
