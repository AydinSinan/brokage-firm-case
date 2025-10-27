package com.pm.brokeragefirm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity(name= "assets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_name", nullable = false, length = 32)
    private String assetName;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal size;

    @Column(name = "usable_size", nullable = false, precision = 19, scale = 4)
    private BigDecimal usableSize;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    private long version;
}
