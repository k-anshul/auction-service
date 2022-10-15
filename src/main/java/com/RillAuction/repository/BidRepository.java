package com.RillAuction.repository;

import com.RillAuction.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, Integer> {

//    @Query(value = "Select b from BidEntity b where b.auctionId = :auction_id order by b.bidValue DESC LIMIT 1")
    Optional<BidEntity> findFirstByAuctionIdOrderByBidValueDesc(@Param("auction_id")int auctionId);

}
