package com.RillAuction.entity;

import com.RillAuction.constants.AuctionState;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction")
@Data
@Accessors(chain = true)
public class AuctionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_id", nullable = false, updatable = false)
    private int productId;

    @Column(name = "seller_id", nullable = false, updatable = false)
    private int sellerId;

    @Column(name = "start_time", nullable = false, updatable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "start_bid_value", nullable = false, updatable = false)
    private float startBidValue;

    @Column(name = "extension", nullable = false)
    private int extension;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AuctionState state;
}
