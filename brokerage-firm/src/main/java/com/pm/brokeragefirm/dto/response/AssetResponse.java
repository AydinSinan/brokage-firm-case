package com.pm.brokeragefirm.dto.response;

import java.math.BigDecimal;

public record AssetResponse(String assetName, BigDecimal size, BigDecimal usableSize) {}
