package com.RillAuction.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BidResponse {
    private int auctionId;

    private int bidderId;

    private float bidValue;

    private int bidId;

    private boolean isWinningBid;
}
