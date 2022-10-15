package com.RillAuction.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BidRequest {
    @NotNull
    @Min(1)
    private int auctionId;

    @Min(1)
    @NotNull
    private int bidderId;

    @NotNull
    private float bidValue;
}
