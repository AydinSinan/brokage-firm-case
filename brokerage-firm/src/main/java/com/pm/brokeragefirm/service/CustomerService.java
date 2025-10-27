package com.pm.brokeragefirm.service;

import com.pm.brokeragefirm.dto.request.CustomerRequestDto;
import com.pm.brokeragefirm.dto.response.CustomerResponseDto;
import com.pm.brokeragefirm.entity.Customer;
import com.pm.brokeragefirm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto request) {
        if (customerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }

        Customer customer = new Customer();
        customer.setUsername(request.getUsername());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRoles(request.getRoles());
        customer.setCreatedAt(Instant.now());

        Customer saved = customerRepository.save(customer);

        return CustomerResponseDto.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .roles(saved.getRoles())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public Long getIdByUsername(String username) {

        Optional<Customer> customerOptional = customerRepository.findByUsername(username);

        return customerOptional
                .map(Customer::getId)
                .orElseThrow(() -> new UsernameNotFoundException("UserID not found: " + username));
    }
}