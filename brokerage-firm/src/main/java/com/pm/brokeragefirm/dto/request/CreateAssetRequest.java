package com.pm.brokeragefirm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateAssetRequest(@NotBlank(message = "Asset name cannot be blank.") String assetName,
                                 @NotNull(message = "Size cannot be null.") @Positive(message = "Size must be a positive value.") BigDecimal size) {
}
