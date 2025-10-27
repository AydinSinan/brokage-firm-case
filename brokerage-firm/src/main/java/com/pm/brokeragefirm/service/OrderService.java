package com.pm.brokeragefirm.service;

import com.pm.brokeragefirm.dto.request.CreateOrderRequest;
import com.pm.brokeragefirm.dto.response.CancelOrderResponse;
import com.pm.brokeragefirm.dto.response.OrderResponse;
import com.pm.brokeragefirm.dto.response.PageResponse;
import com.pm.brokeragefirm.entity.Order;
import com.pm.brokeragefirm.enums.OrderSide;
import com.pm.brokeragefirm.enums.OrderStatus;
import com.pm.brokeragefirm.exception.AccessDeniedCustomerException;
import com.pm.brokeragefirm.exception.BusinessRuleException;
import com.pm.brokeragefirm.exception.NotFoundException;
import com.pm.brokeragefirm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AccountingService accounting;

    @Transactional
    public OrderResponse create(Long customerId, CreateOrderRequest req) {
        if (req.idempotencyKey() != null && orderRepository.existsByIdempotencyKey(req.idempotencyKey()))
            throw new BusinessRuleException("Duplicate order by idempotency key");


// Reserve balances
        if (req.side() == OrderSide.BUY) {
            accounting.reserveForBuy(customerId, req.assetName(), req.size(), req.price());
        } else {
            accounting.reserveForSell(customerId, req.assetName(), req.size());
        }


        Order saved = orderRepository.save(Order.builder()
                .customerId(customerId)
                .assetName(req.assetName())
                .orderSide(req.side())
                .size(req.size())
                .price(req.price())
                .status(OrderStatus.PENDING)
                .createDate(Instant.now())
                .idempotencyKey(req.idempotencyKey())
                .build());


        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> list(Long customerId, Instant from, Instant to, int page, int size) {
        Page<Order> p = orderRepository.findByCustomerIdAndDateRange(customerId, from, to, PageRequest.of(page, size, Sort.by("createDate").descending()));
        return new PageResponse<>(p.getContent().stream().map(this::toResponse).toList(), p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Transactional
    public CancelOrderResponse cancel(Long orderId, Long actorCustomerId, boolean isAdmin) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
        if (!isAdmin && !Objects.equals(o.getCustomerId(), actorCustomerId))
            throw new AccessDeniedCustomerException();
        if (o.getStatus() != OrderStatus.PENDING)
            throw new BusinessRuleException("Only PENDING orders can be canceled");


        if (o.getOrderSide() == OrderSide.BUY) {
            accounting.releaseOnCancelBuy(o.getCustomerId(), o.getSize(), o.getPrice());
        } else {
            accounting.releaseOnCancelSell(o.getCustomerId(), o.getAssetName(), o.getSize());
        }


        o.setStatus(OrderStatus.CANCELLED);
        return new CancelOrderResponse(orderRepository.save(o).getId(), o.getStatus());
    }

    @Transactional
    public OrderResponse adminMatch(Long orderId) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
        if (o.getStatus() != OrderStatus.PENDING)
            throw new BusinessRuleException("Only PENDING orders can be matched");


        boolean isBuy = o.getOrderSide() == OrderSide.BUY;
        accounting.settleOnMatch(o.getCustomerId(), o.getAssetName(), o.getSize(), o.getPrice(), isBuy);
        o.setStatus(OrderStatus.MATCHED);
        return toResponse(orderRepository.save(o));
    }


    private OrderResponse toResponse(Order o) {
        return new OrderResponse(o.getId(), o.getCustomerId(), o.getAssetName(), o.getOrderSide(), o.getSize(), o.getPrice(), o.getStatus(), o.getCreateDate());
    }
}
