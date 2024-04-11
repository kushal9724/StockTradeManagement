package com.example.TradeManagement.Model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TraderRepository extends MongoRepository<Trader, String> {
    Trader findByUsername(String username);
}
