package com.RillAuction.service;

import com.RillAuction.constants.AuctionState;
import com.RillAuction.dto.AuctionCreateRequest;
import com.RillAuction.dto.AuctionResponse;
import com.RillAuction.dto.AuctionUpdateRequest;
import com.RillAuction.dto.SearchAuctionRequest;
import com.RillAuction.entity.AuctionEntity;
import com.RillAuction.repository.AuctionRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AuctionRequestValidator auctionRequestValidator;

    @Override
    public AuctionResponse createAuction(AuctionCreateRequest request) {
        if (auctionRequestValidator.isAuctionRequestValid(request)) {
            throw new ValidationException("auction exists for given product");
        }
        AuctionEntity entity = new AuctionEntity().setState(AuctionState.CREATED)
                .setExtension(1)
                .setStartTime(request.getStartTime())
                .setStartBidValue(request.getStartBidValue())
                .setEndTime(request.getStartTime().plusHours(1))
                .setProductId(request.getProductId())
                .setSellerId(request.getSellerId());

        entity = auctionRepository.save(entity);
        return getAuctionResponse(entity);
    }

    private AuctionResponse getAuctionResponse(AuctionEntity entity) {
        return AuctionResponse.builder().auctionId(entity.getId())
                .sellerId(entity.getSellerId())
                .extensionNumber(entity.getExtension())
                .endTime(entity.getEndTime())
                .startTime(entity.getStartTime())
                .productId(entity.getProductId())
                .startBidValue(entity.getStartBidValue())
                .auctionState(entity.getState())
                .build();
    }

    public AuctionResponse revokeAuction(AuctionEntity entity) {
        if (entity.getState().getId() >= AuctionState.COMPLETED.getId()) {
            throw new ValidationException("auction already completed/revoked");
        }
        entity.setState(AuctionState.ARCHIVED);
        return getAuctionResponse(entity);
    }

    private AuctionResponse completeAuction(AuctionEntity entity) {
        if (entity.getState().getId() > AuctionState.ACTIVE.getId()) {
            throw new ValidationException("auction already completed/revoked");
        }
        entity.setState(AuctionState.COMPLETED);
        // todo :: save auction results
        return getAuctionResponse(entity);
    }

    @Override
    public AuctionResponse updateAuction(AuctionUpdateRequest request) {
        AuctionEntity entity = auctionRepository.findById(request.getAuctionId()).orElse(null);
        if (entity == null) {
            throw new ValidationException("No auction by Id exists");
        }
        if (request.getAuctionState() == AuctionState.ARCHIVED) {
            return revokeAuction(entity);
        } else if (request.getAuctionState() == AuctionState.COMPLETED) {
            return completeAuction(entity);
        }
        return getAuctionResponse(entity);
    }

    @Override
    public List<AuctionResponse> searchAuction(SearchAuctionRequest request) {
        return null;
    }

    @Override
    public AuctionEntity fetchActiveAuction(int auctionId) {
        AuctionEntity entity = auctionRepository.findById(auctionId).orElse(null);
        // just checking whether end time is elapsed and auction not yet ended by scheduler
        if (entity == null || entity.getState() != AuctionState.ACTIVE || entity.getEndTime().isAfter(LocalDateTime.now())) {
            throw new ValidationException("no active auction exists");
        }
        return entity;
    }

    @Override
    public List<AuctionEntity> fetchAuctionsAwaitingResults() {
        return auctionRepository.fetchAuctionsAwaitingResults();
    }
}
