package com.RillAuction.controller;

import com.RillAuction.dto.BidRequest;
import com.RillAuction.dto.BidResponse;
import com.RillAuction.service.AuctionService;
import com.RillAuction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BidController {
    @Autowired
    private BidService bidService;

    @Autowired
    private AuctionService auctionService;

    @PostMapping("/bid")
    public BidResponse createAuction(@RequestHeader("user-id") int userId,
                                     @RequestBody @Valid BidRequest request) {
        request.setBidderId(userId);
        return auctionService.createBidForAuction(request);
    }

    @GetMapping("/bid/search")
    public List<BidResponse> createAuction(@RequestHeader("user-id") int userId) {
        return bidService.searchBid(userId);
    }
}
