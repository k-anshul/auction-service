package com.RillAuction.controller;

import com.RillAuction.constants.AuctionState;
import com.RillAuction.dto.AuctionCreateRequest;
import com.RillAuction.dto.AuctionResponse;
import com.RillAuction.dto.AuctionUpdateRequest;
import com.RillAuction.dto.SearchAuctionRequest;
import com.RillAuction.service.AuctionService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/auction/{id}")
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
