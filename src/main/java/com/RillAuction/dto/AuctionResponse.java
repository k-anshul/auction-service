package com.RillAuction.dto;

import com.RillAuction.constants.AuctionState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuctionResponse {
    private int auctionId;

    private int productId;

    private int sellerId;

    private LocalDateTime startTime;

    private float startBidValue;

    private LocalDateTime endTime;

    private AuctionState auctionState;

    private int extensionNumber;
    
    private BidResponse highestBid;
}
