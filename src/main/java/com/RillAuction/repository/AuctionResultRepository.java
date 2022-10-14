package com.RillAuction.repository;

import com.RillAuction.entity.AuctionResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionResultRepository extends JpaRepository<AuctionResultEntity, Integer> {
}
