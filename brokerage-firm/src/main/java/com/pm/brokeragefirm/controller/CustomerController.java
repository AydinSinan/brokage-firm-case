package com.pm.brokeragefirm.controller;

import com.pm.brokeragefirm.dto.request.CustomerRequestDto;
import com.pm.brokeragefirm.dto.response.CustomerResponseDto;
import com.pm.brokeragefirm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto request) {
        CustomerResponseDto created = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}