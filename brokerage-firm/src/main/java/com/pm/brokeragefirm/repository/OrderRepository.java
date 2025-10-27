package com.pm.brokeragefirm.repository;

import com.pm.brokeragefirm.entity.Order;
import com.pm.brokeragefirm.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.customerId = :customerId and o.createDate between :from and :to")
    Page<Order> findByCustomerIdAndDateRange(Long customerId, Instant from, Instant to, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    boolean existsByIdempotencyKey(String idempotencyKey);
}
