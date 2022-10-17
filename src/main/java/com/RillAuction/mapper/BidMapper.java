package com.RillAuction.mapper;

import com.RillAuction.dto.BidResponse;
import com.RillAuction.entity.BidEntity;

public class BidMapper {

    public static BidResponse getBidResponse(BidEntity entity) {
        if (entity == null) {
            return null;
        }
        return new BidResponse()
                .setBidId(entity.getId())
                .setBidValue(entity.getBidValue())
                .setAuctionId(entity.getAuctionId())
                .setBidderId(entity.getBidderId())
                .setWinningBid(entity.getIsWinningBid() == 1);
    }
}
