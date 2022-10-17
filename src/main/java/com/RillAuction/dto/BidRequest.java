package com.RillAuction.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BidRequest {
    @NotNull
    @Min(1)
    private int auctionId;

    //set internally
    private int bidderId;

    @NotNull
    private float bidValue;
}
