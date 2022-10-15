package com.RillAuction.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "auction_result")
@Data
@IdClass(AuctionResultEntity.class)
@Accessors(chain = true)
public class AuctionResultEntity implements Serializable {
    @Id
    @Column(name = "auction_id", nullable = false, updatable = false)
    private int auctionId;

    @Id
    @Column(name = "bid_id", nullable = false, updatable = false)
    private Integer bidId;
}