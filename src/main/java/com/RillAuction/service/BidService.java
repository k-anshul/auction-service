package com.RillAuction.service;

import com.RillAuction.dto.BidRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.entity.BidEntity;

public interface BidService {
    BidResponse createBid(BidRequest request);

    BidEntity findMaxBidForAuction(int auctionId);
}
