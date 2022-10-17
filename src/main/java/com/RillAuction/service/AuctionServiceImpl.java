package com.RillAuction.service;

import com.RillAuction.constants.AuctionState;
import com.RillAuction.dto.AuctionCreateRequest;
import com.RillAuction.dto.AuctionResponse;
import com.RillAuction.dto.AuctionUpdateRequest;
import com.RillAuction.dto.BidRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.dto.SearchAuctionRequest;
import com.RillAuction.entity.AuctionEntity;
import com.RillAuction.entity.AuctionEntity_;
import com.RillAuction.entity.BidEntity;
import com.RillAuction.repository.AuctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuctionHelper auctionHelper;

    @Autowired
    private BidService bidService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuctionResponse createAuction(AuctionCreateRequest request) {
        if (!auctionHelper.isAuctionRequestValid(request)) {
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
        log.info("created auction with id {}", entity.getId());
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
                .highestBid(bidService.findWinningBidByAuctionId(entity.getId()))
                .build();
    }

    public AuctionResponse revokeAuction(AuctionEntity entity) {
        if (entity.getState().ordinal() >= AuctionState.COMPLETED.ordinal()) {
            throw new ValidationException("auction already completed/revoked");
        }
        entity.setState(AuctionState.ARCHIVED);
        return getAuctionResponse(entity);
    }

    private AuctionResponse completeAuction(AuctionEntity entity) {
        if (entity.getState().ordinal() > AuctionState.ACTIVE.ordinal()) {
            throw new ValidationException("auction already completed/revoked");
        }
        entity.setState(AuctionState.COMPLETED);
        // todo :: save auction results
        return getAuctionResponse(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(readOnly = true)
    public List<AuctionResponse> searchAuction(SearchAuctionRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuctionEntity> query = cb.createQuery(AuctionEntity.class);
        Root<AuctionEntity> root = query.from(AuctionEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        if (request.getSellerId() != null) {
            predicates.add(cb.equal(root.get(AuctionEntity_.SELLER_ID), request.getSellerId()));
        }
        if (request.getState() != null) {
            predicates.add(cb.equal(root.get(AuctionEntity_.STATE), request.getState()));
        }
        if (request.getStartTime() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(AuctionEntity_.START_TIME), request.getStartTime()));
        }
        if (request.getEndTime() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(AuctionEntity_.END_TIME), request.getEndTime()));
        }
        if (request.getAuctionId() != null) {
            predicates.add(cb.equal(root.get(AuctionEntity_.ID), request.getAuctionId()));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query)
                .getResultStream().map(this::getAuctionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionEntity fetchActiveAuction(int auctionId) {
        AuctionEntity entity = auctionRepository.findById(auctionId).orElse(null);
        // just checking whether end time is elapsed and auction not yet ended/archvied
        if (entity == null || entity.getState().ordinal() > AuctionState.ACTIVE.ordinal() || entity.getEndTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("no active auction exists");
        }
        if (entity.getState() == AuctionState.CREATED) {
            entity.setState(AuctionState.ACTIVE);
        }

        return auctionRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuctionEntity> fetchAuctionsAwaitingResults() {
        return auctionRepository.fetchAuctionsAwaitingResults();
    }

    @Override
    @Transactional
    public boolean addExtensionIfApplicable(AuctionEntity entity) {
        if (Duration.between(LocalDateTime.now(), entity.getEndTime()).compareTo(Duration.ofMinutes(5)) <= 0) {
            int updated = auctionRepository.incrementExtension(entity.getId(), 100);
            if (updated > 0) {
                entity.setEndTime(entity.getEndTime().plusMinutes(10));
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BidResponse createBidForAuction(BidRequest request) {
        AuctionEntity auctionEntity = fetchActiveAuction(request.getAuctionId());
        BidEntity highestBid = bidService.findMaxBidForAuction(auctionEntity.getId());
        if (highestBid != null) {
            // this check is best effort basis
            // it is possible that some parallel transaction has already inserted higher bid by the time this bid is inserted
            // this won't affect the result of the auction though
            if (highestBid.getBidderId() == request.getBidderId()) {
                throw new ValidationException("already highest bid");
            }
            if (highestBid.getBidValue() > 0.95f * request.getBidValue()) {
                throw new ValidationException("bid higher value");
            }
        } else {
            if (auctionEntity.getStartBidValue() > request.getBidValue()) {
                throw new ValidationException(String.format("bid value %f less than start bid %f", request.getBidValue(),
                        auctionEntity.getStartBidValue()));
            }
        }
        addExtensionIfApplicable(auctionEntity);

        return bidService.createBid(request);
    }
}
