package com.pm.brokeragefirm.controller;

import com.pm.brokeragefirm.dto.response.OrderResponse;
import com.pm.brokeragefirm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN_ROLE')")
public class AdminController {

    private final OrderService orderService;

    @PostMapping("/orders/{orderId}/match")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse match(@PathVariable Long orderId) {
        return orderService.adminMatch(orderId);
    }
}