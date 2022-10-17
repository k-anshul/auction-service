package com.RillAuction.scheduler;

import com.RillAuction.entity.AuctionEntity;
import com.RillAuction.repository.AuctionRepository;
import com.RillAuction.service.AuctionHelper;
import com.RillAuction.service.BidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@EnableScheduling
public class AuctionScheduler {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidService bidService;

    @Autowired
    private AuctionHelper auctionHelper;

    Logger logger = LoggerFactory.getLogger(AuctionScheduler.class);

    //repeat after every 60 seconds
    @Scheduled(fixedDelay = 60*1000)
    @Transactional
    public void generateAuctionResult() {
        logger.info("starting scheduler");
        auctionRepository.completeApplicableAuctions();

        List<AuctionEntity> auctions = auctionRepository.fetchAuctionsAwaitingResults();
        if (auctions == null || auctions.isEmpty()) {
            logger.info("no auctions to be completed");
            return;
        }

        logger.info("auction size {}", auctions.size());

        for (AuctionEntity auction : auctions) {
            auctionHelper.generateResultForAuction(auction);
        }
    }
}
