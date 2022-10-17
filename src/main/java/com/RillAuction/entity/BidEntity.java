package com.RillAuction.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
@Data
@Accessors(chain = true)
public class BidEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "auction_id", nullable = false, updatable = false)
    private int auctionId;

    @Column(name = "bidder_id", nullable = false, updatable = false)
    private int bidderId;

    @Column(name = "bid_time", nullable = false, updatable = false)
    private LocalDateTime bidTime;

    @Column(name = "bid_value", nullable = false, updatable = false)
    private float bidValue;

    @Column(name = "is_winning_bid")
    private int isWinningBid;
}
