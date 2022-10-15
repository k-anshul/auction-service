package com.RillAuction.repository;

import com.RillAuction.constants.AuctionState;
import com.RillAuction.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, Integer> {
    AuctionEntity findByProductIdAndState_In(int productId, List<AuctionState> states);

    @Query("Select a from AuctionEntity a LEFT OUTER JOIN AuctionResultEntity r on a.id = r.auctionId " +
            "where (a.state = com.RillAuction.constants.AuctionState.ACTIVE" +
            " or a.state = com.RillAuction.constants.AuctionState.COMPLETED) and r.auctionId is null")
    List<AuctionEntity> fetchAuctionsAwaitingResults();
}
