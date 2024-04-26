package com.example.TradeManagement.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class StockApiResponse {

    @JsonProperty("results")
    private List<StockData> results;

    
    @JsonProperty("ticker")
    private String ticker;

    
    
    public List<StockData> getResults() {
        return results;
    }

    public void setResults(List<StockData> results) {
        this.results = results;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

}



