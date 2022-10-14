package com.RillAuction.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
