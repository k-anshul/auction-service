package com.RillAuction.repository;

import com.RillAuction.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, Integer> {

    /**
     * finds highest bid for auction
     * @param auctionId
     * @return
     */
    Optional<BidEntity> findFirstByAuctionIdOrderByBidValueDesc(@Param("auction_id")int auctionId);

    List<BidEntity> findByBidderId(@Param("bidder_id")int bidderId);

    @Query("select b from BidEntity b where b.isWinningBid = 1")
    Optional<BidEntity> findWinningBidByAuctionId(@Param("auction_id")int auctionId);
}
