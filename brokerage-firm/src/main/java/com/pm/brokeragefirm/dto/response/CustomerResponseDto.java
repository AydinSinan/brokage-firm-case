package com.pm.brokeragefirm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CustomerResponseDto {
    private Long id;
    private String username;
    private String roles;
    private Instant createdAt;
}
