package com.pm.brokeragefirm.entity;

import com.pm.brokeragefirm.enums.OrderSide;
import com.pm.brokeragefirm.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "asset_name", nullable = false, length = 32)
    private String assetName;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_side", nullable = false, length = 8)
    private OrderSide orderSide;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal size;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private OrderStatus status;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false, updatable = false)
    private Instant createDate;

    @Column(name = "idempotency_key", length = 64, unique = true)
    private String idempotencyKey;

    @Version
    private long version;
}
