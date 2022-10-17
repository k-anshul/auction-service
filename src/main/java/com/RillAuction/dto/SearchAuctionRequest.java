package com.RillAuction.dto;

import com.RillAuction.constants.AuctionState;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchAuctionRequest {
    private AuctionState state;

    @Min(1)
    private Integer sellerId;

    private Integer auctionId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
