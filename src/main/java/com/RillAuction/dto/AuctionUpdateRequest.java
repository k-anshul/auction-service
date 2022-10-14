package com.RillAuction.dto;

import com.RillAuction.constants.AuctionState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionUpdateRequest {
    private int auctionId;

    private AuctionState auctionState;
}
