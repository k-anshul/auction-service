package com.RillAuction.constants;

import lombok.Getter;

@Getter
public enum AuctionState {
    CREATED(10),
    ACTIVE(20),
    COMPLETED(30),
    ARCHIVED(40);


    private int id;
    AuctionState(int id) {this.id = id;}
}
