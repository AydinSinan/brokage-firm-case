package com.pm.brokeragefirm.dto.response;

import com.pm.brokeragefirm.enums.OrderStatus;

public record CancelOrderResponse(Long orderId, OrderStatus status) {}
