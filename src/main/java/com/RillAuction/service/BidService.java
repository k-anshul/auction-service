package com.RillAuction.service;

import com.RillAuction.dto.BidRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.entity.BidEntity;

import java.util.List;

public interface BidService {
    BidResponse createBid(BidRequest request);

    BidEntity findMaxBidForAuction(int auctionId);

    BidResponse findById(int id);

    List<BidResponse> searchBid(int bidderId);

    BidResponse findWinningBidByAuctionId(int auctionId);
}
