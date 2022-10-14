package com.RillAuction.service;

import com.RillAuction.constants.AuctionState;
import com.RillAuction.dto.AuctionCreateRequest;
import com.RillAuction.entity.AuctionEntity;
import com.RillAuction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuctionRequestValidator {

    @Autowired
    private AuctionRepository auctionRepository;

    public boolean isAuctionRequestValid(AuctionCreateRequest auctionRequest) {
        // if any active or created auction going on
        AuctionEntity auctionEntity = auctionRepository.findByProductIdAndIsActive(auctionRequest.getProductId(),
                true);
        if (auctionEntity != null && auctionEntity.getState().getId() <= AuctionState.ACTIVE.getId()) {
            return false;
        }

        return true;
    }
}
