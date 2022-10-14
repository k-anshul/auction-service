package com.RillAuction.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "auction_result")
@Data
@Accessors(chain = true)
public class AuctionResultEntity {
    @Column(name = "auction_id", nullable = false, updatable = false)
    private int auctionId;

    @Column(name = "bid_id", nullable = false, updatable = false)
    private Integer bidId;
}