package com.RillAuction.entity;

import com.RillAuction.constants.AuctionState;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction")
@Data
@Accessors(chain = true)
public class AuctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_id", nullable = false, updatable = false)
    private int productId;

    @Column(name = "product_id", nullable = false, updatable = false)
    private int sellerId;

    @Column(name = "product_id", nullable = false, updatable = false)
    private LocalDateTime startTime;

    @Column(name = "product_id", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "product_id", nullable = false, updatable = false)
    private float startBidValue;

    @Column(name = "product_id", nullable = false)
    private int extension;

    @Column(name = "product_id", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AuctionState state;
}
