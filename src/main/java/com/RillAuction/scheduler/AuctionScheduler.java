package com.RillAuction.scheduler;

import com.RillAuction.entity.AuctionEntity;
import com.RillAuction.entity.AuctionResultEntity;
import com.RillAuction.entity.BidEntity;
import com.RillAuction.repository.AuctionResultRepository;
import com.RillAuction.service.AuctionService;
import com.RillAuction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class AuctionScheduler {
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private BidService bidService;

    @Autowired
    private AuctionResultRepository auctionResultRepository;

    //repeat after every 60 seconds0
    @Scheduled(fixedDelay = 60 * 1000)
    @Transactional
    public void generateAuctionResult() {
        List<AuctionEntity> auctions = auctionService.fetchAuctionsAwaitingResults();

        if (auctions == null || auctions.isEmpty()) {
            return;
        }

        for (AuctionEntity auction : auctions) {
            BidEntity highestBid = bidService.findMaxBidForAuction(auction.getId());
            AuctionResultEntity result = new AuctionResultEntity()
                    .setAuctionId(auction.getId())
                    .setBidId(highestBid == null ? null : highestBid.getId());

            auctionResultRepository.save(result);
        }
    }
}
