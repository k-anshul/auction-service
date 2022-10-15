package com.RillAuction.dto;

import com.RillAuction.constants.AuctionState;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
public class SearchAuctionRequest {
    private AuctionState state;

    @Min(1)
    private Integer sellerId;

    @Min(1)
    private Integer auctionId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
