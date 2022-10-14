package com.RillAuction.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Valid
public class AuctionCreateRequest {
    @NotNull
    private Integer productId;

    //set internally from header
    //ideally maintain different objects
    private Integer sellerId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private Float startBidValue;
}
