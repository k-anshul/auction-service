package com.RillAuction.service;

import com.RillAuction.constants.AuctionState;
import com.RillAuction.dto.AuctionCreateRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.entity.AuctionEntity;
import com.RillAuction.entity.BidEntity;
import com.RillAuction.repository.AuctionRepository;
import com.RillAuction.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class AuctionHelper {
    @Autowired
    private BidService bidService;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Transactional
    public void generateResultForAuction(AuctionEntity auction) {
        BidEntity highestBid = bidService.findMaxBidForAuction(auction.getId());
        log.info("result for auction {} winner bid {}", auction.getId(),
                highestBid != null ? highestBid.getId(): null);
        if (highestBid == null) {
            return;
        }
        highestBid.setIsWinningBid(1);
        auction.setState(AuctionState.ARCHIVED);
        auctionRepository.save(auction);
        bidRepository.save(highestBid);
    }

    /**
     * validates that no other auction exists for given product in created or active state
     * can create new auction if previous auction is archived or completed
     * @param auctionRequest
     * @return
     */
    public boolean isAuctionRequestValid(AuctionCreateRequest auctionRequest) {
        // if any active or created auction going on
        AuctionEntity auctionEntity = auctionRepository.findByProductIdAndState_In(auctionRequest.getProductId(),
                List.of(AuctionState.ACTIVE, AuctionState.CREATED));
        if (auctionEntity != null && auctionEntity.getState().ordinal() <= AuctionState.ACTIVE.ordinal()) {
            return false;
        }

        return true;
    }
}
