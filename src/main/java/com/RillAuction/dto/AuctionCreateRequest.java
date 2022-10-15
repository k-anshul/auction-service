package com.RillAuction.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
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
    @FutureOrPresent
    private LocalDateTime startTime;

    @NotNull
    private Float startBidValue;
}
