package com.RillAuction.controller;

import com.RillAuction.dto.AuctionCreateRequest;
import com.RillAuction.dto.AuctionResponse;
import com.RillAuction.dto.AuctionUpdateRequest;
import com.RillAuction.dto.SearchAuctionRequest;
import com.RillAuction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @PostMapping("/auction")
    public AuctionResponse createAuction(@RequestHeader("user-id") int userId,
                                         @RequestBody @Valid AuctionCreateRequest request) {
        request.setSellerId(userId);
        return auctionService.createAuction(request);
    }

    @PutMapping("/auction/{auctionId}")
    public AuctionResponse updateAuction(@RequestHeader("user-id") int userId,
                                         @PathVariable int auctionId,
                                         @RequestBody @Valid AuctionUpdateRequest request) {
        request.setAuctionId(auctionId);
        return auctionService.updateAuction(request);
    }

    // todo :: add pagination
    @GetMapping("/auction/search")
    public List<AuctionResponse> searchAuction(@Valid SearchAuctionRequest searchAuctionRequest) {
        return auctionService.searchAuction(searchAuctionRequest);
    }
}
