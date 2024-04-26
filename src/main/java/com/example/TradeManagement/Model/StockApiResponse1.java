package com.example.TradeManagement.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockApiResponse1 {

    @JsonProperty("close")
    private double close;

    @JsonProperty("symbol")
    private String symbol;

    // Getters and setters
    // You can generate getters and setters using your IDE or manually write them

    // Constructor with arguments
    public StockApiResponse1(double close, String symbol) {
        this.close = close;
        this.symbol = symbol;
    }

}



// public class StockApiResponse {
//     @JsonProperty("Meta Data")
//     private Map<String, String> metaData;

//     @JsonProperty("Time Series (Daily)")
//     private Map<String, Map<String, String>> timeSeries;

//     private List<Stock> stocks;

//     // Default constructor (required by Jackson)
//     public StockApiResponse() {
//     }

//     // Constructor with arguments
//     public StockApiResponse(Map<String, String> metaData, Map<String, Map<String, String>> timeSeries, List<Stock> stocks) {
//         this.metaData = metaData;
//         this.timeSeries = timeSeries;
//         this.stocks = stocks;
//     }

//     // Getters and setters
//     public Map<String, String> getMetaData() {
//         return metaData;
//     }

//     public void setMetaData(Map<String, String> metaData) {
//         this.metaData = metaData;
//     }

//     public Map<String, Map<String, String>> getTimeSeries() {
//         return timeSeries;
//     }

//     public void setTimeSeries(Map<String, Map<String, String>> timeSeries) {
//         this.timeSeries = timeSeries;
//     }

//     public List<Stock> getStocks() {
//         return stocks;
//     }

//     public void setStocks(List<Stock> stocks) {
//         this.stocks = stocks;
//     }
// }