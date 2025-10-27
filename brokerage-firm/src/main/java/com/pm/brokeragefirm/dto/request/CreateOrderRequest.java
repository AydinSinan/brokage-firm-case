package com.pm.brokeragefirm.dto.request;

import com.pm.brokeragefirm.enums.OrderSide;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotBlank String assetName,
        @NotNull OrderSide side,
        @DecimalMin(value = "0.0001") BigDecimal size,
        @DecimalMin(value = "0.0001") BigDecimal price,
        @Size(max = 64) String idempotencyKey
) {}
