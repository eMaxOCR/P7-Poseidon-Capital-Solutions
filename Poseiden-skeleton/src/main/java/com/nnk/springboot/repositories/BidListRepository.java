package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nnk.springboot.domain.Bid;

public interface BidListRepository extends JpaRepository<Bid, Integer> {

}
