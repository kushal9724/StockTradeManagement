package com.example.TradeManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.example.TradeManagement.Model.Service.StockService;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public StockService stockService(RestTemplate restTemplate) {
        return new StockService(restTemplate);
    }
}
