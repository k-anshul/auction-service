package com.RillAuction.service;

import com.RillAuction.dto.BidRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.entity.BidEntity;
import com.RillAuction.mapper.BidMapper;
import com.RillAuction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.RillAuction.mapper.BidMapper.getBidResponse;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private BidRepository bidRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BidResponse createBid(BidRequest request) {
        BidEntity bidEntity = new BidEntity().setBidTime(LocalDateTime.now())
                .setBidValue(request.getBidValue())
                .setBidderId(request.getBidderId())
                .setAuctionId(request.getAuctionId());

        bidEntity = bidRepository.save(bidEntity);
        return getBidResponse(bidEntity);
    }

    @Override
    public BidEntity findMaxBidForAuction(int auctionId) {
        return bidRepository.findFirstByAuctionIdOrderByBidValueDesc(auctionId).orElse(null);
    }

    @Override
    public BidResponse findById(int id) {
        BidEntity entity = bidRepository.findById(id).orElseThrow(ValidationException::new);
        return getBidResponse(entity);
    }

    @Override
    public List<BidResponse> searchBid(int bidderId) {
        return bidRepository.findByBidderId(bidderId)
                .stream()
                .map(BidMapper::getBidResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BidResponse findWinningBidByAuctionId(int auctionId) {
        return getBidResponse(bidRepository.findWinningBidByAuctionId(auctionId).orElse(null));
    }
}
