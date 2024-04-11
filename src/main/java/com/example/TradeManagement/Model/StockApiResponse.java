package com.example.TradeManagement.Model;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class StockApiResponse {
    @JsonProperty("Meta Data")
    private Map<String, String> metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, Map<String, String>> timeSeries;

    private List<Stock> stocks;

    // Default constructor (required by Jackson)
    public StockApiResponse() {
    }

    // Constructor with arguments
    public StockApiResponse(Map<String, String> metaData, Map<String, Map<String, String>> timeSeries, List<Stock> stocks) {
        this.metaData = metaData;
        this.timeSeries = timeSeries;
        this.stocks = stocks;
    }

    // Getters and setters
    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public Map<String, Map<String, String>> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Map<String, Map<String, String>> timeSeries) {
        this.timeSeries = timeSeries;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}

