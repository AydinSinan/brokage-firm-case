package com.pm.brokeragefirm.dto.request;

import lombok.Data;

@Data
public class CustomerRequestDto {
    private String username;
    private String password;
    private String roles;
}
