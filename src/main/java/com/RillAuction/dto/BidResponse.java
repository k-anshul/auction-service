package com.RillAuction.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BidResponse {
    private int auctionId;

    private int bidderId;

    private float bidValue;

    private int bidId;
}
