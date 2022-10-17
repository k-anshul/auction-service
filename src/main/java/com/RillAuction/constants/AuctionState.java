package com.RillAuction.constants;

import lombok.Getter;

@Getter
public enum AuctionState {
    CREATED,
    ACTIVE,
    COMPLETED,
    ARCHIVED;
}
