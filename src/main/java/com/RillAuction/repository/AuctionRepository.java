package com.RillAuction.repository;

import com.RillAuction.constants.AuctionState;
import com.RillAuction.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, Integer> {
    AuctionEntity findByProductIdAndState_In(int productId, List<AuctionState> states);

    /**
     * fetches auction entities in active and completed state for which auction result hasn't been computed yet
     * @return list of entities
     */
    @Query("Select a from AuctionEntity a where a.state = com.RillAuction.constants.AuctionState.COMPLETED")
    List<AuctionEntity> fetchAuctionsAwaitingResults();

    /**
     * increment extension for auction if found less than max allowed value
     * @param id
     * @param maxValue
     * @return
     */
    @Modifying(flushAutomatically = true)
    @Query("update AuctionEntity set extension = extension + 1 where id = :id and extension < :maxValue")
    int incrementExtension(@Param("id") int id, @Param("maxValue") int maxValue);

    @Modifying(flushAutomatically = true)
    @Query("update AuctionEntity set state = com.RillAuction.constants.AuctionState.COMPLETED " +
            "where endTime < NOW() and state in (com.RillAuction.constants.AuctionState.ACTIVE, com.RillAuction.constants.AuctionState.COMPLETED)")
    int completeApplicableAuctions();
}
