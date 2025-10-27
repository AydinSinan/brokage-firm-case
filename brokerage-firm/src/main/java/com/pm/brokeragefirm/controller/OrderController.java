package com.pm.brokeragefirm.controller;

import com.pm.brokeragefirm.dto.request.CreateOrderRequest;
import com.pm.brokeragefirm.dto.response.CancelOrderResponse;
import com.pm.brokeragefirm.dto.response.OrderResponse;
import com.pm.brokeragefirm.dto.response.PageResponse;
import com.pm.brokeragefirm.service.CustomerService;
import com.pm.brokeragefirm.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    private Long customerIdFromAuth(Authentication auth) {
        String username = auth.getName();
        return customerService.getIdByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(Authentication auth, @Valid @RequestBody CreateOrderRequest req) {
        return orderService.create(customerIdFromAuth(auth), req);
    }

    @GetMapping
    public PageResponse<OrderResponse> list(Authentication auth,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size) {
        return orderService.list(customerIdFromAuth(auth), from, to, page, size);
    }

    @PutMapping("/admin/match/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse adminMatch(@PathVariable Long orderId) {
        return orderService.adminMatch(orderId);
    }

    @DeleteMapping("/{orderId}")
    public CancelOrderResponse cancel(Authentication auth, @PathVariable Long orderId) {
        return orderService.cancel(orderId, customerIdFromAuth(auth), false);
    }
}
