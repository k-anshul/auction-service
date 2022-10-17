package com.RillAuction.service;

import com.RillAuction.dto.AuctionCreateRequest;
import com.RillAuction.dto.AuctionResponse;
import com.RillAuction.dto.AuctionUpdateRequest;
import com.RillAuction.dto.BidRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.dto.SearchAuctionRequest;
import com.RillAuction.entity.AuctionEntity;

import java.util.List;

public interface AuctionService {
    AuctionResponse createAuction(AuctionCreateRequest request);

    AuctionResponse updateAuction(AuctionUpdateRequest request);

    List<AuctionResponse> searchAuction(SearchAuctionRequest request);

    AuctionEntity fetchActiveAuction(int auctionId);

    List<AuctionEntity> fetchAuctionsAwaitingResults();

    boolean addExtensionIfApplicable(AuctionEntity entity);

    BidResponse createBidForAuction(BidRequest bidRequest);
}
