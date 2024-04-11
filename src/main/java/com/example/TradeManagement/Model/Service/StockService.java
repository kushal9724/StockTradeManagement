package com.example.TradeManagement.Model.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import com.example.TradeManagement.Model.Stock;
import com.example.TradeManagement.Model.StockApiResponse;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StockService {

    // private static final String apiKey = "MPBS7SE4HCCC7OCP";
    private static final String apiKey = "8RI3IKQID2QHVQYR";
    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&" + apiKey;

    private final RestTemplate restTemplate;

    public StockService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Stock> fetchStockData() {
        // Make API call to fetch stock data
        StockApiResponse response = restTemplate.getForObject(API_URL, StockApiResponse.class);
        
        // Extract stock data from the API response and construct Stock objects
        List<Stock> stocks = new ArrayList<>();
        for (Stock stockData : response.getStocks()) {
            Stock stock = new Stock(stockData.getSymbol(), stockData.getName(), stockData.getPrice(), stockData.getQuantity(), stockData.getDate(), stockData.getGain(), stockData.getValue());
            stocks.add(stock);
        }
        
        return stocks;
    }

    public Stock fetchStockData(String tickerSymbol, String date, int quantity) {
        System.out.println(12);
        System.out.println(date);
        // Construct the API URL with the provided ticker symbol and API key
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + 
                tickerSymbol + "&apikey=" + apiKey;


        // Make API call to fetch stock data
        StockApiResponse response = restTemplate.getForObject(apiUrl, StockApiResponse.class);
        
        // Extract the necessary information from the API response
        // (e.g., stock price) and calculate the total price
        double stockPrice = calculateStockPrice(response, date);
        // double totalPrice = stockPrice * quantity;
        double gain = 0;
        double value = quantity * stockPrice;
        System.out.println( "q "  + quantity);
        System.out.println( "p "  + stockPrice);
        // Create a new Stock object with the calculated total price
        Stock stock = new Stock(tickerSymbol, "", stockPrice, quantity, date, gain, value);
        
        return stock;
    }

    private double calculateStockPrice(StockApiResponse response, String date) {
        // Get the Time Series data from the response
        Map<String, Map<String, String>> timeSeries = response.getTimeSeries();
        
        System.out.println("time series "+ timeSeries);
        System.out.println("response  " + response);

        // Check if the response contains Time Series data and the specified date is present
        if (timeSeries != null && timeSeries.containsKey(date)) {
            // Get the stock data for the specified date
            Map<String, String> stockData = timeSeries.get(date);
            
            // Extract the closing price from the stock data
            String closePrice = stockData.get("4. close");
            if (closePrice != null) {
                // Convert the closing price to double and return
                return Double.parseDouble(closePrice);
            }
        }
    
        // If the specified date or closing price is not found, return a default value or handle the error as needed
        return 0.0; // or throw an exception, log an error, etc.
    }


    public double getCurrentPrice(String tickerSymbol) {
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + 
                tickerSymbol + "&apikey=" + apiKey;
        
        // Make API call to fetch stock data
        StockApiResponse response = restTemplate.getForObject(apiUrl, StockApiResponse.class);
        LocalDate today = LocalDate.now();

        // Subtract one day to get yesterday's date
        LocalDate yesterday = today.minusDays(1);

        // Format yesterday's date as a string
        String date = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        double stockPrice = calculateStockPrice(response, date);
        
        return stockPrice;
    }

    
}
