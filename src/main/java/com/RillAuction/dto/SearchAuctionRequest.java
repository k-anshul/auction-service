package com.RillAuction.dto;

import com.RillAuction.constants.AuctionState;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchAuctionRequest {
    private AuctionState state;

    @Min(1)
    private Integer sellerId;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
