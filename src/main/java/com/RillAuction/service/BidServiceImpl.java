package com.RillAuction.service;

import com.RillAuction.dto.BidRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.entity.AuctionEntity;
import com.RillAuction.entity.BidEntity;
import com.RillAuction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.time.LocalDateTime;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private BidRepository bidRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BidResponse createBid(BidRequest request) {
        AuctionEntity auctionEntity = auctionService.fetchActiveAuction(request.getAuctionId());
        BidEntity highestBid = findMaxBidForAuction(auctionEntity.getId());
        // this check is best effort basis
        // it is possible that some parallel transaction has already inserted higher bid by the time this bid is inserted
        // this won't affect the result of the auction though
        if (highestBid != null && highestBid.getBidValue() > 0.95f * request.getBidValue()) {
            throw new ValidationException("bid higher value");
        }
        BidEntity bidEntity = new BidEntity().setBidTime(LocalDateTime.now())
                .setBidValue(request.getBidValue())
                .setBidderId(request.getBidderId())
                .setAuctionId(request.getAuctionId());

        bidEntity = bidRepository.save(bidEntity);
        return getBidResponse(bidEntity);
    }

    @Override
    public BidEntity findMaxBidForAuction(int auctionId) {
        return bidRepository.findFirstByAuctionIdOrderByBidValueDesc(auctionId).orElse(null);
    }

    private BidResponse getBidResponse(BidEntity entity) {
        return new BidResponse()
                .setBidId(entity.getId())
                .setBidValue(entity.getBidValue())
                .setAuctionId(entity.getAuctionId())
                .setBidderId(entity.getBidderId());
    }
}
